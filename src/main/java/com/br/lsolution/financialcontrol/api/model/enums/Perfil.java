package com.br.lsolution.financialcontrol.api.model.enums;

public enum Perfil {
    ADMIN(1, "ROLE_ADMIN"),
    CLIENTE(2, "ROLE_CLIENTE");

    private int code;
    private String descricao;

    private Perfil(int code, String descricao) {
        this.code = code;
        this.descricao = descricao;
    }

    public int getCode() {
        return code;
    }

    public String getDescricao() {
        return descricao;
    }

    public static Perfil toEnum(Integer code) {
        if (code == null) {
            return null;
        } else {
            for (Perfil x : Perfil.values()) {
                if (code.equals(x.getCode())) {
                    return x;
                }
            }
        }
        throw new IllegalArgumentException("Id inválido: " + code);
    }
}

