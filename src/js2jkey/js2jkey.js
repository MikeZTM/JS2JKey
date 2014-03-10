$('#chatBody_ul').bind("DOMSubtreeModified",function(){
	var key=$('#chatBody_ul').children().last().children().last().children().last().contents(":not(span)").text().slice(0,1);
	//alert(key);
 $.ajax({
            url: "http://127.0.0.1:8000/test?key="+key,
            type: "GET"
        });
});