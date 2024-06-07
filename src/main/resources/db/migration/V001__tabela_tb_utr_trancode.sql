CREATE TABLE tb_utr_trancode (
  utr_sq_trancode int NOT NULL AUTO_INCREMENT,
  ugr_sq_grupo int NOT NULL,
  utr_ds_trancode varchar(50) NOT NULL,
  auditoria_data timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  auditoria_usuario varchar(50) NOT NULL,
  PRIMARY KEY (utr_sq_trancode),
  KEY fk_tb_utr_permissao_tb_ugr_grupo (ugr_sq_grupo),
  CONSTRAINT fk_tb_utr_permissao_tb_ugr_grupo FOREIGN KEY (ugr_sq_grupo) REFERENCES tb_ugr_grupo (ugr_sq_grupo)
) ENGINE=InnoDB AUTO_INCREMENT=305 DEFAULT CHARSET=latin1 COMMENT='Trancodes com a permissão para exibição de telas';