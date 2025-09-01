package br.com.fiap.brinquedos.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "TDS_TB_BRINQUEDOS")
@Data
public class Brinquedo {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "brinquedo_seq")
    @SequenceGenerator(name = "brinquedo_seq", sequenceName = "TDS_TB_BRINQUEDOS_SEQ", allocationSize = 1)
    private Long id;

    private String nome;
    private String tipo;
    private String classificacao;
    private String tamanho;
    private Double preco;
}