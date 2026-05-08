	var mensagemResultado 		= null;
	var table 					= null;	
	var quantidadeLinha 		= null;	
	var requisicao 				= null;
	var trOpened 				= null;
	var hide 					= true;
	var temDados 				= false;
	var igual 					= false;
	var dadosRepetidos 			= "";
	var msgDetalhe				= "";
	var ctxPopup 				= "/YMPopup/popup.html";
	var msg 					= "";
	var lastRowChild 			= null;
	var map;
    	  
	  function initMap(id, address, local,zoom) {
	   var geocoder = new google.maps.Geocoder();
	   
	    map = new google.maps.Map(document.getElementById(id), {
          zoom: zoom,
		  fullscreenControl: true
        });
	  
		geocoder.geocode( { 'address': address}, function(results, status) {
		if (status == 'OK') {
			map.setCenter(results[0].geometry.location);
			var marker = new google.maps.Marker({
				map: map,
				position: results[0].geometry.location,
				title: local
			});
					
			if(isMobile()){
				navigator.geolocation.getCurrentPosition(function(location) {
					var suaPosicao = new google.maps.LatLng(location.coords.latitude, location.coords.longitude);
					setRouteMobile(map, suaPosicao,results[0].geometry.location);
				});				
			}

		} else {
			unknownAddressMap(map, "<div>Oficina não localizada.</div>");			
		}
		});
	}
	
	function unknownAddressMap(map, content){
		var latLog = new google.maps.LatLng(-13.4991118,-69.7725694,4);
			
			map.setCenter(latLog);
			var marker = new google.maps.Marker({
				map: map,
				position: latLog,
			});
			
			showTooltipMap(map, marker, content, true);
	}
	
	function setRouteMobile(map, addressOrig,addressDest, content) {
	var directionsService = new google.maps.DirectionsService();
    var directionsDisplay = new google.maps.DirectionsRenderer(); 
    directionsDisplay.setMap(map);

   var request = {
      origin: addressOrig, 
      destination: addressDest, 
      travelMode: google.maps.TravelMode.DRIVING 
   };
 
   directionsService.route(request, function(result, status) {
     if(status == google.maps.DirectionsStatus.OK) { 
         directionsDisplay.setDirections(result); 
     }
	 else{
		unknownAddressMap(map, content);	
	 }
   });

}
		
	function showTooltipMap(map, marker, contentString, isVisible){
		
        var infowindow = new google.maps.InfoWindow({
          content: contentString
        });
	
		if(isVisible){
			infowindow.open(map, marker);
		}
		
		marker.addListener('click', function() {
          infowindow.open(map, marker);
        });
	
	}	
		
	function callError(componente, msg){
		exibeMsgError("ERRO", msg);
	}
	
	function callBackChosen(retorno, componente, attr, dataType, comportamento){
		var someObjStr = getJsonDataType(retorno, dataType);
		loadChosen(componente, someObjStr.retorno[attr], dataType);
		
	}
	
	function addEvents(componente, comportamento) {
	
	$("#" + componente + " tbody").off();
	$("#" + componente + " tbody").on('click', 'td.detalhes', function() {
		
		var tr 				= $(this).closest('tr');
		var row 			= table.row(tr);
		var trOpenedInfo 	= null;
		
		if(trOpened != null){
			trOpenedInfo 	= table.row(trOpened).data().id;
		}
		
		var rowInfo 		= table.row(tr).data().id;

		if(trOpenedInfo == dadosRepetidos){
			dadosRepetidos 	= rowInfo;
			igual 			= true;
		}else{
			igual 			= false;
		}
		
		if (trOpened != null && trOpenedInfo != rowInfo)
			closeRow(trOpened);
		   	
		if(trOpenedInfo == rowInfo && table.row( tr ).child.isShown()){
			closeRow(tr);
		} else {
			loadingAtivo = true;
			openRow(tr, comportamento);
		}
	});
}

function closeRow(tr) {
	var row = table.row( tr );
	row.child.hide();
	tr.removeClass('shown');
	trOpened = null;
	tr.removeAttr("style");
	tr.each(function(){
	   $(this).find('td').each(function(){
	       	$(this).removeAttr("style");
	   });
	});
	
}

function openRow(tr, comportamento) {
	lastRowChild = table.row( tr );
	//row.child(msgDetalhe).show(); 
	//setTimeout(function(){ 
	//}, 1500);
	
	if(!igual){
		comportamento(lastRowChild.data());	
	}
	
    tr.addClass('shown');
    trOpened = tr;
    tr.css("background-color", "rgb(208, 207, 207) !important");
    tr.css("font-weight", "bold");

    tr.each(function(){
        $(this).find('td').each(function(){
        	$(this).css("border-top", "1px solid #203147");
        	$(this).css("border-bottom", "1px solid #203147");
        	$(this).css("color", "#203147");
        });
    });

    ajustaFrame(5);
    
    if(igual){
    	//finalizarLoading();
    }
}

function reRenderChild(msgDetalhe) {
	
	
	lastRowChild.child(msgDetalhe).show(); 
}

function ajustaFrame(porcentagem) {

	var x = ($(".divBox").height() * (1 + porcentagem/100)) + "px";

	if($('iframe[name="via-corretor-yasuda-gestao"]', window.parent.document) != undefined
		&& $('iframe[name="via-corretor-yasuda-gestao"]', window.parent.document).length == 1){
		
		$('iframe[name="via-corretor-yasuda-gestao"]', window.parent.document).height(x);	
		
	}else if($('iframe[name="via-corretor-yasuda-via-corretor-yasuda"]', window.parent.document) != undefined
			&& $('iframe[name="via-corretor-yasuda-via-corretor-yasuda"]', window.parent.document).length == 1){
		
		$('iframe[name="via-corretor-yasuda-via-corretor-yasuda"]', window.parent.document).height(x);
		
	}else{
		$('iframe[name="iframeApp"]', window.parent.document).height(x);
	}
}

		function dimensionaTable(json){
			
			var lastKey = null;
			var lastValue = null;
			
			if(!isMobile()) {			
				$.each(json, function (key, value){
					$("#" + key).css("width", value);
					$("#" + lastKey).css("min-width", lastValue);
					lastKey = key;
					lastValue = value;
				});
							
				$("#" + lastKey).css("min-width", lastValue);						
			} 
			
		
		//var tipoBrowser = navigator.userAgent;
		//	if (tipoBrowser.toUpperCase().indexOf("MOZILLA") >= 0){
			//	$("#" + lastKey).css("min-width", lastValue);
		//	}			
	   }
	   
	function callBackDetalhe(retorno, componente, attr, dataType, comportamento){
		retorno = getJsonDataType(retorno, dataType);
		
		if(retorno.retorno!=undefined){
			comportamento(retorno.retorno[attr], componente);
		}
		else{
			comportamento(undefined, componente);
		}
	}
	
	function getJsonDataType(retorno,dataType){
		var responseTemp = retorno;
		
		if(responseTemp == null){
			return responseTemp;
		}
		
		try{
			if(dataType.startsWith("text")){
				responseTemp = JSON.parse(responseTemp);
			}
		}catch(e){
			responseTemp = null;
			exibeMsgError("ERRO", "Não foi possível exibir os detalhes do componente, favor tentar novamente.");
		}
		return responseTemp;
	}
		
    function isMobile() { 
		var userAgent = navigator.userAgent.toLowerCase(); 
		if( userAgent.search(/(android|avantgo|blackberry|bolt|boost|cricket|docomo|fone|hiptop|mini|mobi|palm|phone|pie|tablet|up\.browser|up\.link|webos|wos)/i)!= -1 )	return true; 
		return false;
	} 
		
	function callBackTable(retorno, componente, attr, dataType, comportamento){
		var responseTemp = getJsonDataType(retorno, dataType);
		var data = responseTemp.retorno[attr];
				
	//Dados guardados para montagem de planilha
	quantidadeLinha 	    = data.length;

	if(responseTemp.retorno[attr].length > 0){
		mensagemResultado = responseTemp.retorno.mensagem;
	}
		
	try{
		
		if(mensagemResultado == null || mensagemResultado == ""){
			$(".divBntAction").css("display", "block");
			$("#" + componente).css("display", "block");
			$("#" + componente + "_wrapper").css("display", "block");
			$("#msgError").css("display","none");
			$("#msgError span").html("");
			if(table != null){
				table.clear().draw();
				table.destroy();
			}
			temDados = true;
		}else{
			
			//Define tamanho iframe
			
			temDados = false;
			loadingAtivo = true;
			exibeMsgError("ALERTA", mensagemResultado, componente);
			return false;
		}
		
		var columns = new Array();
	
		if (isMobile()) {
			var size = responseTemp.retorno.columns.length - 1;
			var i = 0; 
			
			$(responseTemp.retorno.columns).each(function(a, b) {
				if (i != 0)
					b["width"] = (100 / size) + "%";
				
				i++;				
				columns.push(b);
			});

			console.log(columns);
		}
		
		table = $("#" + componente).DataTable( {
			"responsive": true,
			"autoFill": {columns: ':not(:first-child)'},
			"fnDrawCallback": function () {$('[data-toggle="tooltip"]').tooltip();},
			"fnFooterCallback": function(nRow, aaData, iStart, iEnd, aiDisplay){
				
				if (trOpened != null) closeRow(trOpened);
				 
				var valorAtual = iEnd - iStart;
				
				if (table != null) {
					if (valorAtual == 10) {
						if (qtdeAnterior == 10) {
							ajustaFrame(30);
						} else {
							if (aiDisplay.length > valorAtual && qtdeAnterior > 0){
								ajustaFrame(hide ? 50 : 40);
							}else if (qtdeAnterior == 0){
								ajustaFrame(30);
							}else{
								ajustaFrame(20);
							}
						}
					} else {
						if (qtdeAnterior < valorAtual) {
							ajustaFrame(hide ? 30 : 20);
						} else {
							ajustaFrame(aiDisplay.length > 0 ? valorAtual * 2.5 : 15);
						}
					}
				}
				
				qtdeAnterior = valorAtual;

			},
			"fnClearTable" : true,
			"data": data,
	        "dataType": "json",
	        "contentType": "application/json; charset=utf-8",
			"type": "POST",
			"bLengthChange": false,
			"paging":   true,
	        "ordering": true,
	        "info":     true,
			"columns": (isMobile() ? columns : responseTemp.retorno.columns),
				"fnDrawCallback": function( oSettings ) {
				     addEvents(componente, comportamento);
				},
				"order": [[ 2, "asc" ]],
				
				"language": {
				"search": "Busca na Lista:", 
				"paginate": {
					"first":      "Primeiro",
					"last":       "Último",
					"next":       "Próximo",
					"previous":   "Anterior"
				},
	            "lengthMenu": "Exibir _MENU_ linhas por página",
	            "sInfoFiltered":"", /* (filtered from _MAX_ total records) */
	            "zeroRecords": "Nenhuma informação encontrada. Por favor, altere os dados e refaça sua busca.",
	            "info": "Mostrando página _PAGE_ de _PAGES_",
	            "infoEmpty": "Nenhuma página disponível",
	            "infoFiltered": "(filtered from _MAX_ total records)"}
		} );
		
		$("#" + componente).css("display", "block");
		$("#" + componente + "_filter").addClass("personalizeTable");
		
		//Display none para quando a apólice estiver selecionada
		if(requisicao != null && requisicao != ""){
			$("#" + componente + "_filter").css("display", "none");
		}
		
		dimensionaTable(responseTemp.retorno.dimtable);
		
		if (temDados) {
			if(quantidadeLinha != 0 && quantidadeLinha <= 3){
				ajustaFrame(20);
			}else if(quantidadeLinha != 0 && quantidadeLinha <= 6){
				ajustaFrame(30);
			}else if(quantidadeLinha != 0 && quantidadeLinha > 6){
				ajustaFrame(50);
			}

		} else {
			$("#msgError span").html("");
			$(".divBntAction").css("display", "none");
			exibeMsgError(	"ALERTA",
					"Não foi possível encontrar dados com a pesquisa informada. Por favor, altere os dados e refaça sua busca.", componente);
			
			if(document.getElementById(componente).style.display == 'block'){
				ajustaFrame(20);
			}
		}
		
		}finally{
			//finalizarLoading();
		}
	}
	
	function ocultaTabela(table){
		
		var componente = null;
		
		try{
			componente = $("#" + table + "_wrapper");
		}catch(exception){
			componente = table;
		}
		componente.css("display", "none");
	}
		
	function callAjax(url,callBack, callError, componente, dataType, attr, comportamento, msgErr){
		
		showLoading();
						
		$.ajax({
				type:     "POST",
				url:      url,
				dataType: dataType,
				crossDomain: true,
				success:  function(retorno){
					try{
						callBack(retorno, componente, attr, dataType, comportamento);
						hideLoading();
					} catch(e){
						hideLoading();
					}
				},
				timeout:  9000, 
				error:    function(){
					hideLoading();
					callError(componente, msgErr);
				}
			 }
		);		
	}
		
	function loadChosen(component, json, dataType) {

	var comp;
	try {
		comp = $("#" + component);
	} catch (exception) {
		comp = component;
	}

	comp.find('option').remove();
	comp.append(new Option("Selecione", ""));
	
	if(json != null){
		for (var i = 0; i < json.length; i++) {
			comp.append(new Option(json[i].label, json[i].val));
		}
	}

	comp.chosen({
		width : '100%',
		no_results_text : "Oops, sem resultado para "
	});
	
	if ($('.chosen-container').length > 0) {
      $('.chosen-container').on('touchstart', function(e){
        //e.preventDefault();
		e.stopPropagation(); 
        $(this).trigger('mousedown');
      });
    }

	comp.trigger("chosen:updated");
	}
	
	
	function exibeMsgError(type, mensagemErro, componente){
		
	if(isMobile()){	
		alert(mensagemErro);
	}else{
		
	var title = "";
	$("#msgError").removeAttr("class");
	
	if(type == "ERRO") {
		$("#msgError").attr("class", "alert alert-danger");
		title= "ERRO!";
	} else if (type == "ALERTA") {
		$("#msgError").attr("class", "alert alert-warning");
		title="Atenção!";
	} else if (type == "INFO") {
		$("#msgError").attr("class", "alert alert-info");
		title="Informativo!";
	} else if (type == "SUCESSO") {
		$("#msgError").attr("class", "alert alert-success");
		title="Sucesso!";
	}
			
	$("#msgError span").html("");
	$("#msgError strong").html(title);
	$("#msgError span").append("<br />" + mensagemErro);
	$("#msgError").css("display","block");

	if(temDados){
		if(quantidadeLinha != 0 && quantidadeLinha <= 3){
			ajustaFrame(20);
		}else if(quantidadeLinha != 0 && quantidadeLinha <= 6){
			ajustaFrame(30);
		}else if(quantidadeLinha != 0 && quantidadeLinha > 6){
			ajustaFrame(50);
		}	
	}else{
		if(document.getElementById(componente).style.display == 'block'){
			ajustaFrame(20);
		}else{
			ajustaFrame(200);
		}
	}
	
	}
}