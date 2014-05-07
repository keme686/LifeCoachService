$(document).ready(function() {

	//////////////////////////////////////////////////
	////////////////SIGN IN //////////////////////////
	//////////////////////////////////////////////////
	$('#btnSignInUser').click(function() {
		var user = {
		        username: $('#loginUsername').val(),
		        password: $('#loginPassword').val()
		    }; 

		    $.post("/lifecoach/UserLogin", user, function(data){		    			    	
		    	window.location = "/lifecoach/index.jsp";
		    	return true;
		    });
		    return false;
	});
	//////////////////////////////////////////////////////
	///////////////////SIGN UP/////////////////////////////
	///////////////////////////////////////////////////////
	$('#btnSignupNew').click(function(){
		
		var selectedVal = "";
		var selected = $("input[type='radio'][name='inputGender']:checked");
		if (selected.length > 0) {
		    selectedVal = selected.val();
		}
		
		var userdata = {
			name: $('#inputName').val(),
			username: $('#inputUsername').val(),
			password:  $('#inputPassword').val(),
			email:  $('#inputEmail').val(),
			gender:  selectedVal,
			dateofbirth:  $('#inputDateOfBirth').val(),
			mobile:  $('#inputMobile').val(),
			location: $('#inputLocation').val()
		};
		
		$.post("/lifecoach/Signup", userdata, function(data){
	    	window.location = "/lifecoach/account/home.jsp";	    	
	    	return true;
	    });
		
		return false;
	});
	///////////////////////////////////////////////////////
	////////////SIGN OUT///////////////////////////////////
	///////////////////////////////////////////////////////
	$('#btnSignOutUser').click(function(){
		$.post("/lifecoach/SignOut", {}, function(data){
	    	window.location = "/lifecoach/index.jsp";	    	
	    	return true;
	    });
		
		return false;
	});
	
	
	
	 /*$.ajax({
	        url: "/order_items/change/"+$(this).attr("data-order-item-id")+"/qty:"+$(this).val()+"/returnas.json",
	        type: "POST",
	        dataType: "json",
	        qty_input: $(this),
	        anything_else_i_want_to_pass_in: "foo",
	        success: function(json_data, textStatus, jqXHR) {
	             here is the input, which triggered this AJAX request 
	            console.log(this.qty_input);
	             here is any other parameter you set when initializing the ajax method 
	            console.log(this.anything_else_i_want_to_pass_in);
	        }
	    });*/
	
	
});