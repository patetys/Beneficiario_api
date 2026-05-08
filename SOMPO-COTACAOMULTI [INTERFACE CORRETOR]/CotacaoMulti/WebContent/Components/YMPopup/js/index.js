function initPopup(component) {
	$(component).html("<div id='dialog-confirm'></div>");
}
/**
 * 
 */
function hideLoading() {
	setTimeout(function() {
		$("#dialog-confirm").dialog('close')
	}, 500);
}

function showLoading() {
	$("body").attr("onresize", "resizePopup()");
	callbackHandler(); 
};

function callbackHandler() {
	apresentarPopup();
	$('div[tabindex=-1]').css("background-image", "url('/Components/YMPopup/images/89.GIF')");
	$('div[tabindex=-1]').css("background-repeat", "no-repeat");
	$('ui-dialog').css("height", "350px !important");
	$('div[tabindex=-1]').css("width", "300px !important");
	$('div[tabindex=-1]').css("background-color", "transparent");
	$('div[tabindex=-1]').css("border", "none");
	
	$(".ui-widget-header").css("background","none");
	$(".ui-widget-header").css("border","none");
}

function apresentarPopup() {
		$("#dialog-confirm").dialog({
			closeOnEscape : false,
			resizable : false,
			height : 100,
			width : 80,
			modal : true,
			
			open : function(event, ui) {
				$(".ui-dialog-titlebar-close", ui.dialog | ui).hide();
			},
			
			show : {
				effect : "fadeIn",
				duration : 500
			},
			
			hide : {
				effect : "fadeOut",
				duration : 500
			}
		});
	
}


function resizePopup() {
	var w = window,
	d = document,
	e = d.documentElement,
	g = d.getElementsByTagName('body')[0],
	x = w.innerWidth || e.clientWidth || g.clientWidth,
	y = w.innerHeight|| e.clientHeight|| g.clientHeight;
	var div = $('div[tabindex=-1]');
	div.css("left", ((x/2) - 40)+"px");
	div.css("top", ((y/2) - 60)+"px");
}