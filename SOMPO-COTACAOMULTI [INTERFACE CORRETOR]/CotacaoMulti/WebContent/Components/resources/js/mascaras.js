function chama_mascara(o) {
    // Limpa o valor para contar apenas os caracteres relevantes
    var valorLimpo = o.value.replace(/[^a-z0-9]/ig, '');

    // A decisão agora é baseada no tamanho do valor limpo
    if (valorLimpo.length > 11) {
        mascara(o, cnpj);
    } else {
        mascara(o, cpf);
    }
}

function mascara(o, f) {
    v_obj = o;
    v_fun = f;
    setTimeout("execmascara()", 1);
}

function execmascara() {
    v_obj.value = v_fun(v_obj.value);
}

function telefone(v) {
    v = v.replace(/\D/g, "");
    v = v.replace(/^(\d\d)(\d)/g, "($1) $2");
    v = v.replace(/(\d{4})(\d)/, "$1-$2");
    return v;
}

function cpf(v) {
    v = v.replace(/\D/g, "");
    v = v.replace(/(\d{3})(\d)/, "$1.$2");
    v = v.replace(/(\d{3})(\d)/, "$1.$2");
    v = v.replace(/(\d{3})(\d{1,2})$/, "$1-$2");
    return v;
}

function cep(v) {
    v = v.replace(/\D/g, ""); // Corrigido /D/g para /\D/g
    v = v.replace(/^(\d{2})(\d)/, "$1.$2");
    v = v.replace(/(\d{3})(\d{1,2})$/, "$1-$2");
    return v;
}

function cnpj(v) {
    // 1. Limpa, mantendo letras e números, e força para maiúsculas.
    v = v.replace(/[^a-z0-9]/ig, '').toUpperCase();

    // 2. Limita o tamanho para 14 caracteres.
    v = v.substring(0, 14);

    // 3. Aplica a máscara alfanumérica passo a passo.
    // A verificação final (d{2}) garante que os últimos 2 são numéricos.
    if (v.length > 12) {
        v = v.replace(/^(\w{2})(\w{3})(\w{3})(\w{4})(\d{2})$/, '$1.$2.$3/$4-$5');
    } else {
        v = v.replace(/^(\w{2})(\w)/, "$1.$2");
        v = v.replace(/^(\w{2})\.(\w{3})(\w)/, "$1.$2.$3");
        v = v.replace(/\.(\w{3})(\w)/, ".$1/$2");
        v = v.replace(/\/(\w{4})(\w)/, "/$1-$2");
    }

    return v;
}