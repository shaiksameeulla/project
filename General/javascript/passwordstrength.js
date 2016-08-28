

//For Password Strength

var pass_strength;

function IsEnoughLength(str,length)
{
	if ((str == null) || isNaN(length))
		return false;
	else if (str.length < length)
		return false;
	return true;
	
}

function HasMixedCase(passwd)
{
	if(passwd.match(/([a-z].*[A-Z])|([A-Z].*[a-z])/))
		return true;
	else
		return false;
}

function HasNumeral(passwd)
{
	if(passwd.match(/[0-9]/))
		return true;
	else
		return false;
}

function HasSpecialChars(passwd)
{
	if(passwd.match(/.[!,@,#,$,%,^,&,*,?,_,~]/))
		return true;
	else
		return false;
}

function CheckPasswordStrength()
{
	var pwd = $$("password").value;
	if (IsEnoughLength(pwd,14) && HasMixedCase(pwd) && HasNumeral(pwd) && HasSpecialChars(pwd))
		pass_strength = "Very strong";
	else if (IsEnoughLength(pwd,8) && HasMixedCase(pwd) && HasNumeral(pwd) && HasSpecialChars(pwd))
		pass_strength = "Strong";
	else if (IsEnoughLength(pwd,8) && HasNumeral(pwd) && HasSpecialChars(pwd))
		pass_strength = "Good";
	else 
		pass_strength = "Weak";
	$('#strength').html(getPwdStrengthColor(pass_strength));
}

function getPwdStrengthColor(strengthStr){
	var divStr="";
	if(strengthStr == "Weak"){
		divStr='<div class="pwstrength" style="background-color : rgba(255,0,0,1);"></div>';
		divStr+='<div class="pwstrength" style="background-color : rgba(255,0,0,0.8);"></div>';
		divStr+='<div class="pwstrength" style="background-color : rgba(255,0,0,0.6);"></div>';
		divStr+='<div class="pwstrength" style="background-color : rgba(255,0,0,0.4);"></div>';
		divStr+='<div class="pwstrength" style="background-color : rgba(255,0,0,0.2);"></div>';
	}else if(strengthStr == "Good"){
		divStr='<div class="pwstrength" style="background-color : rgba(255,0,0,0.8);"></div>';
		divStr+='<div class="pwstrength" style="background-color : rgba(255,0,0,0.4);"></div>';
		divStr+='<div class="pwstrength" style="background-color : rgba(255,0,0,0);"></div>';
		divStr+='<div class="pwstrength" style="background-color : rgba(0,255,0,0.4);"></div>';
		divStr+='<div class="pwstrength" style="background-color : rgba(0,255,0,0.8);"></div>';
	}else if(strengthStr == "Strong"){
		divStr='<div class="pwstrength" style="background-color : rgba(0,255,0,1);"></div>';
		divStr+='<div class="pwstrength" style="background-color : rgba(0,255,0,0.8);"></div>';
		divStr+='<div class="pwstrength" style="background-color : rgba(0,255,0,0.6);"></div>';
		divStr+='<div class="pwstrength" style="background-color : rgba(0,255,0,0.4);"></div>';
		divStr+='<div class="pwstrength" style="background-color : rgba(0,255,0,0.2);"></div>';
	}else if(strengthStr == "Very strong"){
		divStr='<div class="pwstrength" style="background-color : rgba(0,255,0,1);"></div>';
		divStr+='<div class="pwstrength" style="background-color : rgba(0,255,0,1);"></div>';
		divStr+='<div class="pwstrength" style="background-color : rgba(0,255,0,1);"></div>';
		divStr+='<div class="pwstrength" style="background-color : rgba(0,255,0,1);"></div>';
		divStr+='<div class="pwstrength" style="background-color : rgba(0,255,0,1);"></div>';
	}  
	divStr+='<div style="float:left;font-size: 10pt;color: #333333;">'+strengthStr+'</div>';
	return divStr;
}