package util;

import br.com.sompo.cotacaomulti.exception.InvalidCnpjCpfException;

public class CpfCnpjValidator {

    //----------------------------------------------------------------------------------------
    // CPF

    private static final int TAMANHO_CPF_SEM_DV = 9;
    private static final String REGEX_FORMACAO_BASE_CPF = "[\\d]{9}";
    private static final String REGEX_VALOR_ZERADO_CPF = "^(\\d)\\1{10}$"; // Evita sequências do tipo 00000000000 ou 11111111111
    private static final int[] CPF_PESOS_DV1 = {10, 9, 8, 7, 6, 5, 4, 3, 2};
    private static final int[] CPF_PESOS_DV2 = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};

    //----------------------------------------------------------------------------------------
    // CNPJ

    private static final int TAMANHO_CNPJ_SEM_DV = 12;
    private static final String REGEX_FORMACAO_BASE_CNPJ = "[A-Z\\d]{12}";
    private static final String REGEX_VALOR_ZERADO_CNPJ = "^[0]+$";
    private static final int VALOR_BASE = (int) '0';
    private static final int[] CNPJ_PESOS = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

    //----------------------------------------------------------------------------------------
    // Comum

    private static final int TAMANHO_MAX = 14;
    private static final String REGEX_FORMACAO_DV = "[\\d]{2}";
    private static final String REGEX_CARACTERES_FORMATACAO = "[./-]";

    private static String removeCaracteresFormatacaoCpfCnpj(String src) {
        return src.trim().replaceAll(REGEX_CARACTERES_FORMATACAO, "").toUpperCase();
    }

    //-----------------------------------------------------------------------------------------

    public static boolean isValidCpf(String cpf) {
        if (cpf != null) {
            cpf = removeCaracteresFormatacaoCpfCnpj(cpf);
            if (cpf.length() <= TAMANHO_MAX) {
                cpf = removerZerosDoCpf(cpf); // remove os zeros a esquerda para validar
                if (isCpfFormacaoValidaComDV(cpf)) {
                    String dvInformado = cpf.substring(TAMANHO_CPF_SEM_DV);
                    String dvCalculado = calculaDVCpf(cpf.substring(0, TAMANHO_CPF_SEM_DV));
                    return dvCalculado.equals(dvInformado);
                }
            }
        }
        return false;
    }

    private static int calculaDigitoCpf(String cpfBase, int[] pesos) {
        int soma = 0;
        for (int i = 0; i < cpfBase.length(); i++) {
            int digito = Integer.parseInt(cpfBase.substring(i, i + 1));
            soma += digito * pesos[i];
        }
        int resto = soma % 11;
        return (resto < 2) ? 0 : 11 - resto;
    }

    private static String calculaDVCpf(String novePrimeirosDigitos) {
        if (isCpfFormacaoValidaSemDV(novePrimeirosDigitos)) {
            int dv1 = calculaDigitoCpf(novePrimeirosDigitos, CPF_PESOS_DV1);
            String baseParaDv2 = novePrimeirosDigitos + dv1;
            int dv2 = calculaDigitoCpf(baseParaDv2, CPF_PESOS_DV2);

            return String.format("%d%d", dv1, dv2);
        }

        throw new InvalidCnpjCpfException("CPF não é válido para o cálculo do digito.");
    }

    private static boolean isCpfFormacaoValidaSemDV(String cpf) {
        return cpf.matches(REGEX_FORMACAO_BASE_CPF) && !cpf.matches(REGEX_VALOR_ZERADO_CPF);
    }

    private static boolean isCpfFormacaoValidaComDV(String cpf) {
        return cpf.matches(REGEX_FORMACAO_BASE_CPF.concat(REGEX_FORMACAO_DV)) && !cpf.matches(REGEX_VALOR_ZERADO_CPF);
    }

    public static String preencherCpfComZeros(String cnpjCpf) {
        if (cnpjCpf == null) {
            return "";
        }

        cnpjCpf = removeCaracteresFormatacaoCpfCnpj(cnpjCpf);

        if (cnpjCpf.length() >= TAMANHO_MAX) {
            return cnpjCpf;
        }

        return String.format("%" + TAMANHO_MAX + "s", cnpjCpf).replace(' ', '0');
    }

    public static String removerZerosDoCpf(String cpf) {
        if (cpf == null || cpf.length() == 11) {
            return cpf;
        }
        cpf = removeCaracteresFormatacaoCpfCnpj(cpf);
        while (cpf.length() > 11 && cpf.startsWith("0")) {
            cpf = cpf.substring(1);
        }
        return cpf;
    }

    //-------------------------------------------------------------------

    public static boolean isValidCnpj(String cnpj) {
        if (cnpj != null) {
            cnpj = removeCaracteresFormatacaoCpfCnpj(cnpj);
            if (cnpj.length() == TAMANHO_MAX) {
                if (isCnpjFormacaoValidaComDV(cnpj)) {
                    String dvInformado = cnpj.substring(TAMANHO_CNPJ_SEM_DV);
                    String dvCalculado = calculaDVCnpj(cnpj.substring(0, TAMANHO_CNPJ_SEM_DV));

                    return dvCalculado.equals(dvInformado);
                }
            }
        }
        return false;
    }

    private static int calculaDigitoCnpj(String cnpj) {
        int soma = 0;
        for (int indice = cnpj.length() - 1; indice >= 0; indice--) {
            int valorCaracter = (int) cnpj.charAt(indice) - VALOR_BASE;
            soma += valorCaracter * CNPJ_PESOS[CNPJ_PESOS.length - cnpj.length() + indice];
        }
        return soma % 11 < 2 ? 0 : 11 - (soma % 11);
    }

    private static String calculaDVCnpj(String baseCnpj) {
        if (baseCnpj != null) {
            baseCnpj = removeCaracteresFormatacaoCpfCnpj(baseCnpj);
            if (isCnpjFormacaoValidaSemDV(baseCnpj)) {
                String dv1 = String.format("%d", calculaDigitoCnpj(baseCnpj));
                String dv2 = String.format("%d", calculaDigitoCnpj(baseCnpj.concat(dv1)));

                return dv1.concat(dv2);
            }
        }
        throw new InvalidCnpjCpfException("CNPJ não é válido para cálculo do digito.");
    }

    private static boolean isCnpjFormacaoValidaSemDV(String cnpj) {
        return cnpj.matches(REGEX_FORMACAO_BASE_CNPJ) &&
                !cnpj.matches(REGEX_VALOR_ZERADO_CNPJ);
    }

    private static boolean isCnpjFormacaoValidaComDV(String cnpj) {
        return cnpj.matches(REGEX_FORMACAO_BASE_CNPJ.concat(REGEX_FORMACAO_DV)) &&
                !cnpj.matches(REGEX_VALOR_ZERADO_CNPJ);
    }

    public static boolean eCpfOuCnpjValido(String documento) {
        if (documento == null || documento.trim().isEmpty()) {
            return false;
        }

        String docLimpo = removeCaracteresFormatacaoCpfCnpj(documento);

        try {
            // Verifica se é um CPF válido
            if (docLimpo.length() == 11) {
                return isValidCpf(docLimpo);
            }
            // Verifica se é um CNPJ válido
            if (docLimpo.length() == 14) {
                return isValidCnpj(docLimpo);
            }
        } catch (InvalidCnpjCpfException e) {
            return false;
        } catch (Exception e) {
            return false;
        }

        // Se o tamanho não for 11 nem 14, é inválido.
        return false;
    }

    //----------------------------------------------------------------------
    // Metodo que valida se é um cnpj ou cpf válido e já retorna no formato correto para salvar.

    public static String validarCpfCnpjParaSalvar(String src) {
        if (src == null || src.isEmpty()) {
            throw new InvalidCnpjCpfException("CNPJ/CPF inválido.");
        }

        String campoLimpo = removeCaracteresFormatacaoCpfCnpj(src); // Removo os possiveis caracteres especiais [./-]

        if (campoLimpo.length() <= TAMANHO_MAX) {                   // Está no tamanho válido para um cpf ou cnpj
            String cpfSemZeros = removerZerosDoCpf(campoLimpo);

            if (cpfSemZeros.length() == 11) {                       // Tamanho de um cpf, agora eu valido e retorno caso seja válido!
                boolean isCpf = isValidCpf(cpfSemZeros);
                if (isCpf) {
                    return preencherCpfComZeros(campoLimpo);
                }
            } else if (campoLimpo.length() == 14) {
                boolean isCnpj = isValidCnpj(campoLimpo);           // É um possivel cnpj, aqui eu válido e se for
                if (isCnpj) {
                    return campoLimpo;                              // Retorno
                }
            }
        }

        throw new InvalidCnpjCpfException(String.format("CNPJ/CPF %s não é válido.", src));
    }

    public static String validarParaBuscar(String cpfCnpj){
        if (eCpfOuCnpjValido(cpfCnpj)) {
            cpfCnpj = removeCaracteresFormatacaoCpfCnpj(cpfCnpj);
            return preencherCpfComZeros(cpfCnpj);
        }
        return cpfCnpj;
    }

    public static String formataCpfCnpj(String cpfCnpj) {
        if (cpfCnpj == null || cpfCnpj.isEmpty()) {
            return cpfCnpj;
        }

        String docLimpo = removeCaracteresFormatacaoCpfCnpj(cpfCnpj);

        if (docLimpo.length() == 11) {
            return preencherCpfComZeros(docLimpo);
        } else if (docLimpo.length() == 14) {
            return docLimpo;
        }

        return cpfCnpj;
    }
}