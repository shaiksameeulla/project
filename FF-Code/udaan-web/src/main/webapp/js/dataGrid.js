function createImgElement(src, title){
	var img = document.createElement("img");
	img.setAttribute("src", src);
	img.setAttribute("title", title);
	return img;
}

function createInputTextElement(name,id,value,size,align){
	return createInputNode(inputType,name,id,value,size,align);
	
}

function createTextAreaElement(name, id, size){
	var textElement = document.createElement("textarea");
	textElement.setAttribute("name",name);
	textElement.setAttribute("id",id);
	textElement.setAttribute("cols","15");
	textElement.setAttribute("rows","1");
	textElement.align = "center";
	return textElement;
}

function createHiddenElement(name, id,value){
	return createInputNode(hiddenType,name,id,value,null,null);
}

function createCheckBoxElement(name, id){
	var checkBoxElement = document.createElement("input");
	checkBoxElement.type = "checkbox";
	checkBoxElement.name = name;
	checkBoxElement.id = id;
	//checkBoxElement.align = "center";
	return checkBoxElement;
}
function createInputNode(typeName,name,id,value,size,align){
	var textElement=createNode(inputType,typeName,name,id);
	textElement.value=value;
	if(!isNull(size)){
		textElement.size = size;
	} 
	if(!isNull(align)){
		textElement.align = align;
	}
	return textElement;
}
function createNode(inputType,typeName,name,id){
	var textElement = document.createElement(inputType);
	textElement.type = typeName;
	textElement.name = name;
	textElement.id = id;
}
