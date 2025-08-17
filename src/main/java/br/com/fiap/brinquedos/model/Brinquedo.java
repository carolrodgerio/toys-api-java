package br.com.fiap.brinquedos.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "TDS_TB_BRINQUEDOS")
@Data
public class Brinquedo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String tipo;
    private String classificacao;
    private String tamanho;
    private Double preco;
}