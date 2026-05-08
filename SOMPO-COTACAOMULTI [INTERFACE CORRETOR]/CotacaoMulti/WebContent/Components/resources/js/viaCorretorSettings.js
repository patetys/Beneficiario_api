i18n.init({ 
	lng: "pt_br",
	fixLng: true
	}, function(err, translate) {
	$('[data-i18n]').i18n();
  
  $(document).ready(function() {

	  $("#listTable_filter label input").attr("placeholder",translate("grid.placeholderBusca"));
	  
  });
  
});


/*
 * Validar os campos
data media de 15 dias
Se selecionar o endosso, apolice é obrigatoria

Loading
susep

mensagens de erro
 * 
 */

