package com.grupogbs.bot.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.grupogbs.bot.entities.dtos.delsoft.ConsultaOrdemServicoDTO;
import com.grupogbs.bot.entities.dtos.delsoft.ItemOrdemServicoDTO;
import com.grupogbs.bot.entities.dtos.delsoft.OrdemServicoDTO;
import com.grupogbs.bot.entities.dtos.senior.RespostaConsultaColabotadorDTO;

@Service
public class OrdemServicoService {
	
	@Autowired
	@Qualifier("delsoftJdbcTemplate")
	private JdbcTemplate delsoftJdbcTemplate;
	
	@Autowired
	private ConsultaColaboradorService consultaColaboradorService;
	
	@Autowired
	private MensagemService mensagemService;
	
	public List<OrdemServicoDTO> buscarDados(){
		
		String sql = """
				SELECT 
				CASE 
					WHEN A.FILCGC = 68 THEN 6
					WHEN A.FILCGC = 16 THEN 17
					ELSE A.FILCGC
				END AS empresa,
				a.MN20NROOS AS numeroOS,
				a.MN20CHVACE AS prefixoVeiculo, 
				a.MN20DTAOS AS aberturaOS, 
				a.MN20DTAENC AS encerramentoOS, 
				a.MN20USUCOD AS matriculaUsuario, 
				E.MN01DEFDES AS motivoAbertura, 
				B.MN20SITCOD AS status,
				G.MN60DEFDES AS resolucao, 
				C.MN20APTOBS AS observacaoResolucao,
				C.MN20EXEUSU AS usuarioExecucao,
				D.USUNOME AS nomeUsuarioExecucao
				FROM mn020 a 
				LEFT OUTER JOIN mn0202 b ON B.FILCGC = A.FILCGC AND b.MN20NROOS = A.MN20NROOS AND B.MN20SITCOD = 'Encerrada' 
				LEFT OUTER JOIN MN0203 C ON C.FILCGC = A.FILCGC AND C.MN20NROOS = A.MN20NROOS AND C.MN20TARSEQ = B.MN20TARSEQ
				LEFT OUTER JOIN USU01 D ON C.MN20EXEUSU = D.USUCOD
				LEFT OUTER JOIN MN0011 E ON B.MN20TPCODT = E.MN01TPCOD AND B.MN20DEFCDT = E.MN01DEFCOD
				LEFT OUTER JOIN MN061 F ON C.MN20NROOS = F.MN20NROOS AND C.FILCGC = F.FILCGC AND C.MN20TARSEQ = F.MN20TARSEQ
				LEFT OUTER JOIN MN0602 G ON G.MN60TPDEF = F.MN60TPDEF AND G.MN60DEF = F.MN60DEF AND G.MN60ARECOD = B.MN20ARECOD
				WHERE ((CAST( A.MN20DTAENC AS DATE) >= '2024-07-07')
						AND ((A.MN20DTAENC >= CURRENT TIMESTAMP - 14 DAYS)
						AND     (SUBSTR(A.MN20USUCOD,1,1) = '0'
						OR       SUBSTR(A.MN20USUCOD,1,1) = '1'
						OR       SUBSTR(A.MN20USUCOD,1,1) = '2'
						OR       SUBSTR(A.MN20USUCOD,1,1) = '3'
						OR       SUBSTR(A.MN20USUCOD,1,1) = '4'
						OR       SUBSTR(A.MN20USUCOD,1,1) = '5'
						OR       SUBSTR(A.MN20USUCOD,1,1) = '6'
						OR       SUBSTR(A.MN20USUCOD,1,1) = '7'
						OR       SUBSTR(A.MN20USUCOD,1,1) = '8'
						OR       SUBSTR(A.MN20USUCOD,1,1) = '9')))
				ORDER BY A.MN20USUCOD, a.MN20NROOS
				""";
		
		List<ConsultaOrdemServicoDTO> l = delsoftJdbcTemplate.query(sql, (rs, rowNum) -> new ConsultaOrdemServicoDTO(
				rs.getInt("empresa"),
				rs.getLong("numeroOS"),
				rs.getString("prefixoVeiculo"),
				rs.getObject("aberturaOS", LocalDateTime.class),
				rs.getObject("encerramentoOS", LocalDateTime.class),
				rs.getString("matriculaUsuario"),
				rs.getString("motivoAbertura"),
				rs.getString("status"),
				rs.getString("resolucao"),
				rs.getString("observacaoResolucao"),
				rs.getString("usuarioExecucao"),
				rs.getString("nomeUsuarioExecucao"),
				null,
				null
				));
	
		// Removendo valores que contenham letras maiúsculas.
		l = l.stream().filter(OS -> !OS.getMatriculaUsuario()
				.chars()
				.anyMatch(Character::isUpperCase))
				.toList();
		
		// Adicionando celular e e-mail do requerente ao DTO de Ordem de Serviço.
		l = l.stream().map(OS -> adicionarInfoContato(OS)).toList();
		
		List<OrdemServicoDTO> listaParaEnvio = agruparOrdensServico(l);
		
		// Remover mensagens que já foram enviadas e retornar os dados.
		return listaParaEnvio.stream().filter(OS -> !mensagemService.verificarMensagemEnviada(OS)).toList();
	}
	
	
	
	public List<OrdemServicoDTO> agruparOrdensServico(List<ConsultaOrdemServicoDTO> consultasOrdemServico){
		
		Map<Long, OrdemServicoDTO> controle = new HashMap<>();
		
		for(ConsultaOrdemServicoDTO c : consultasOrdemServico) {
			if(controle.containsKey(c.getNumeroOS())) {
				List<ItemOrdemServicoDTO> l = new ArrayList<>(); 
				l.addAll(controle.get(c.getNumeroOS()).getItens());
				l.add(new ItemOrdemServicoDTO(c));
				controle.get(c.getNumeroOS()).setItens(l);
			}
			else {
				OrdemServicoDTO os = new OrdemServicoDTO(c);
				controle.put(os.getNumeroOS(), os);
			}
		}
		
		List<OrdemServicoDTO> ordensServico = new ArrayList<>(controle.values());
		
		for(OrdemServicoDTO os : ordensServico) {
			
			// Contagem de itens de cada OS.
			int count = 0;
		
			StringBuilder sb = new StringBuilder();
			StringBuilder sbWpp = new StringBuilder();
			
			for(ItemOrdemServicoDTO i : os.getItens()) {
				
				// Limitando numero de itens para cada OS.
				if(count < 4) {
					sb.append((count + 1) + " - ").append(i.getRelatoAbertura()).append("\n");
					sb.append("Solução: ").append(i.getSolucao()).append("\n");
					sb.append("Obs: ").append(i.getObservacaoSolucao()).append("\n");
					sb.append("Por: ").append(i.getUsuarioExecucao()).append("\n\n");
					
					sbWpp.append((count + 1) + " - ").append(i.getRelatoAbertura()).append("\\n");
					sbWpp.append("Solução: ").append(i.getSolucao()).append("\\n");
					sbWpp.append("Obs: ").append(i.getObservacaoSolucao()).append("\\n");
					sbWpp.append("Por: ").append(i.getUsuarioExecucao()).append("\\n\\n");
					
					count += 1;
				}
		
			}
			
			os.setItensTexto(sb.toString());
			os.setItensTextoWhatsApp(sbWpp.toString());
		}
				
		return new ArrayList<>(controle.values());
		
	}
	
	public ConsultaOrdemServicoDTO adicionarInfoContato(ConsultaOrdemServicoDTO ordemServico) {
		
		RespostaConsultaColabotadorDTO resp = consultaColaboradorService.consultar(ordemServico.getMatriculaUsuario().strip(), 
				ordemServico.getEmpresa());
		
		// Adicionando o telefone.
		
		String telefone = null;
		
		telefone = formatarCelular(resp.getCelular(), resp, "A");
		if(telefone == null) telefone = formatarCelular(resp.getTelefoneFixoA(), resp, "A");
		if(telefone == null) telefone = formatarCelular(resp.getTelefoneFixoB(), resp, "B");
		
		ordemServico.setCelularRequerente(telefone);
		
		// Adicionando o e-mail (inicialmente, apenas o e-mail da empresa será adicionado.
		
		ordemServico.setEmailRequerente(resp.getEmailEmpresa() != null && !resp.getEmailEmpresa().isBlank()
				? resp.getEmailEmpresa().strip() : null);
		
		return ordemServico;
	}
	
	private String formatarCelular(String celular, RespostaConsultaColabotadorDTO ordemServico, 
			String prioridadeDDD) {
		
		// Checagem de null
		if(celular == null || celular.strip().isBlank()) {
			return null;
		}
		// Verificando se o número possui 11 ou 9 caracteres. Caso não, ele não pode ser utilizado.
		if(celular.length() != 11 && celular.length() != 9) {
			return null;
		}
		
		// Verificando se existe o dígito 9, para ser um celular.
		if(celular.length() == 9 && celular.charAt(0) != '9') {
			return null;
		}
		
		String codigoPais = "55";
		
		//Expressão regular \D para encontrar todos os caracteres que não são dígitos 
		//e substitui cada um deles por uma string vazia (efetivamente removendo-os).
		celular = celular.replaceAll("\\D", "").replaceAll(" ", "");
		
		String telefone;
			
		// Lógica para quando o número está completo.
		if(celular.length() == 11)telefone = codigoPais + celular;
		
		// Lógica para quando o número não está completo.
		else {
			// Checando a prioridade de DDD para completar o número, que está sem DDD.
			if(prioridadeDDD.equals("A")) {
				telefone = codigoPais 
						+ Optional.ofNullable(ordemServico.getDddFixoA()).orElse(ordemServico.getDddFixoB()) 
						+ celular;
			}
			else {
				telefone = codigoPais 
						+ Optional.ofNullable(ordemServico.getDddFixoB()).orElse(ordemServico.getDddFixoA()) 
						+ celular;
			}
		}
			
		// Remover os caracteres e espaços mais uma vez, por garantia.
		telefone = telefone.replaceAll("\\D", "").replaceAll(" ", "");
		
		
		// Retornando o telefone já formatado.
		return telefone;
		
	}

}
