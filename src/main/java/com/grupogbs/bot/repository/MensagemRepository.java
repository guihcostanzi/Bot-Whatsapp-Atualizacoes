package com.grupogbs.bot.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.grupogbs.bot.entities.Mensagem;

@Repository
public interface MensagemRepository extends JpaRepository<Mensagem, Long> {
	
	Optional<Mensagem> findByNumeroOSAndDestinatario(Long numeroOS, String destinatario);
}
