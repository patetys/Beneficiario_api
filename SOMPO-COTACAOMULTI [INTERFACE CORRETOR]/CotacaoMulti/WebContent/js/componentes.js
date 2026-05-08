function loadChosen(component, json, dataType, val, label) {
	
	if(json != null && json != '' && dataType == 'text'){
		json = JSON.parse(json);
	}

	var comp;
	try {
		comp = $("#" + component);
	} catch (exception) {
		comp = component;
	}

	comp.find('option').remove();
	comp.append(new Option("Selecione", ""));

	if (json != null) {
		for (var i = 0; i < json.length; i++) {
			comp.append(new Option(json[i][label], json[i][val]));
		}
	}

	comp.chosen({
		width : '100%',
		no_results_text : "Oops, sem resultado para "
	});

	if ($('.chosen-container').length > 0) {
		$('.chosen-container').on('touchstart', function(e) {
			e.stopPropagation();
			e.preventDefault();
			// Trigger the mousedown event.
			$(this).trigger('mousedown');
		});
	}

	comp.trigger("chosen:updated");
}

function selectedChosen(component, val, subVal) {

	var comp;
	var chosenComp;
	
	if(val == null){
		val = "";
	}
	
	try {
		comp = $("#"+component+" option:contains('" + val + "')");
		chosenComp = $("#" + component);
	} catch (exception) {
		comp = component.find("option:contains('" + val + "')"); 
		chosenComp = component;
	}
	
	comp.attr({ selected : "selected" });
	
	if ($('.chosen-container').length > 0) {
		$('.chosen-container').on('touchstart', function(e) {
			e.stopPropagation();
			e.preventDefault();
			// Trigger the mousedown event.
			$(this).trigger('mousedown');
		});
	}

	chosenComp.trigger("chosen:updated");

}