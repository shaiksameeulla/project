/*
Keyboard-accessible jQuery drop-down menu
by Michael Tangen - http://www.designbymichael.com
Developed for the State of Minnesota - http://mn.gov
Rev 2
This menu supports up to five levels in all, including the top level navigation.
First level is horizontal, all other child menus are automatically placed based upon parent location.

*/
// shortcut references
var firstlevel = $(topLevelUL);
var secondlevel = $(firstlevel).children('li').children('ul');
var thirdlevel = $(secondlevel).children('li').children('ul');
var fourthlevel = $(thirdlevel).children('li').children('ul');
var fifthlevel = $(fourthlevel).children('li').children('ul');
var sixthlevel = $(fifthlevel).children('li').children('ul');


$(document).ready(function(event) {
	// go through and apply arrow class
	$(topLevelUL + ' li ul li').each(function(index, element) {
		$(this).has('ul').children('a').addClass('rightarrow');
	});
	
	var isTouchDevice = $('html').hasClass('touch'); // check for touch devices
	// ############ LEVEL ONE
	// ############ hide and show second level
	// show second level items on mouseover
	$(firstlevel).children('li').mouseenter(function(event) {
		widthOffset = adjustPlacement($(this), Number($(this).width()));
		heightOffset = Number($(this).height());
		showElement($(this).children('ul'), widthOffset-widthOffset, heightOffset, true);  // we zero out the width offset for the first level
	});
		
		
	// keyboard tab in through first level
	$(firstlevel).children('li').focusin(function(event) {
		// Hide all child ul's first
		hideElement($(firstlevel).children('li').children('ul'));
		
		// gather width and height values of li item
		widthOffset = adjustPlacement($(this), Number($(this).width()));
		heightOffset = Number($(this).height());
		
		showElement($(this).children('ul'), widthOffset-widthOffset, heightOffset, false);  // we zero out the width offset for the first level
	});
	// mouseout on first level li's 
	$(firstlevel).children('li').mouseleave(function(event) {
		hideElement($(firstlevel).children('li').children('ul'));
	});
	$(firstlevel).children('li').focusout(function(event) {
		hideElement($(firstlevel).children('li').children('ul'));
	});
	// for touch devices, when clicking in the site content area, hide sub navigation
	if (isTouchDevice == true) {
		$('#site_content').click(function(event) {
			hideElement($(firstlevel).children('li').children('ul'));
		});
	}
	$(document).keyup(function(e) {
		if (e.keyCode == 27) { // esc	
			hideElement($(firstlevel).children('li').children('ul'));
			$(firstlevel).children('li').children('a').removeClass('toplevelhoverfocus');
		}   
	});
	
	
	// ############ LEVEL TWO
	// ############ hide and show third level
	// show third level items on mouseover
	$(secondlevel).children('li').mouseenter(function(event) {
		widthOffset = adjustPlacement($(this), Number($(this).width()));
		heightOffset = Number($(this).height());
		showElement($(this).children('ul'), widthOffset, 0, true);  // zero out the height offset - want on the same line
		$(this).parent('ul').parent('li').children('a').addClass('toplevelhoverfocus');
		
	});
	// keyboard tab in through third level
	$(secondlevel).children('li').focusin(function(event) {
		// Hide all child ul's first
		hideElement($(secondlevel).children('li').children('ul'));
		// gather width and height values of li item
		widthOffset = adjustPlacement($(this), Number($(this).width()));
		heightOffset = Number($(this).height());
		showElement($(this).children('ul'), widthOffset, 0, false); // zero out the height offset - want on the same line
		$(this).parent('ul').parent('li').children('a').addClass('toplevelhoverfocus');
	});
	// mouseout on second level li's 
	$(secondlevel).children('li').mouseleave(function(event) {
		hideElement($(secondlevel).children('li').children('ul'));
		$(this).parent('ul').parent('li').children('a').removeClass('toplevelhoverfocus');
	});
	$(secondlevel).children('li').focusout(function(event) {
		hideElement($(secondlevel).children('li').children('ul'));
		$(this).parent('ul').parent('li').children('a').removeClass('toplevelhoverfocus');
	});
	
	
	// ############ LEVEL THREE
	// ############ hide and show fourth level
	// show fourth level items on mouseover
	$(thirdlevel).children('li').mouseenter(function(event) {
		widthOffset = adjustPlacement($(this), Number($(this).width()));
		heightOffset = Number($(this).height());
		showElement($(this).children('ul'), widthOffset, 0, true);  // zero out the height offset - want on the same line
		// dim second level links
		$(secondlevel).children('li').each(function(index, element) {
			$(this).children('a').addClass('dimText');
		});
		$(this).parent('ul').parent('li').children('a').removeClass('dimText');
		$(this).parent('ul').parent('li').children('a').removeClass('rightarrow');
		$(this).parent('ul').parent('li').children('a').addClass('right2arrowhover');
	});
	// keyboard tab in through fourth level
	$(thirdlevel).children('li').focusin(function(event) {
		// Hide all child ul's first
		hideElement($(thirdlevel).children('li').children('ul'));
		// gather width and height values of li item
		widthOffset = adjustPlacement($(this), Number($(this).width()));
		heightOffset = Number($(this).height());
		showElement($(this).children('ul'), widthOffset, 0, false); // zero out the height offset - want on the same line
		// dim second level links
		$(secondlevel).children('li').each(function(index, element) {
			$(this).children('a').addClass('dimText');
		});
		$(this).parent('ul').parent('li').children('a').removeClass('dimText');
		$(this).parent('ul').parent('li').children('a').removeClass('rightarrow');
		$(this).parent('ul').parent('li').children('a').addClass('right2arrowhover');
		
	});
	// mouseout on third level li's 
	$(thirdlevel).children('li').mouseleave(function(event) {
		hideElement($(thirdlevel).children('li').children('ul'));
		// undo dim second level links
		$(secondlevel).children('li').each(function(index, element) {
			$(this).children('a').removeClass('dimText');
		});
		$(this).parent('ul').parent('li').children('a').removeClass('right2arrowhover');
		$(this).parent('ul').parent('li').children('a').addClass('rightarrow');
	});
	$(thirdlevel).children('li').focusout(function(event) {
		// dim second level links
		$(secondlevel).children('li').each(function(index, element) {
			$(this).children('a').removeClass('dimText');
		});
		$(this).parent('ul').parent('li').children('a').removeClass('right2arrowhover');
		$(this).parent('ul').parent('li').children('a').addClass('rightarrow');
	});
	
	
	// ############ LEVEL FOUR
	// ############ hide and show fifth level
	// show fifth level items on mouseover
	$(fourthlevel).children('li').mouseenter(function(event) {
		widthOffset = adjustPlacement($(this), Number($(this).width()));
		heightOffset = Number($(this).height());
		showElement($(this).children('ul'), widthOffset, 0, true);  // zero out the height offset - want on the same line
		// dim third level links
		$(thirdlevel).children('li').each(function(index, element) {
			$(this).children('a').addClass('dimText');
		});
		$(this).parent('ul').parent('li').children('a').removeClass('dimText');
		$(this).parent('ul').parent('li').children('a').removeClass('rightarrow');
		$(this).parent('ul').parent('li').children('a').addClass('right3arrowhover');
	});
	// keyboard tab in through fourth level
	$(fourthlevel).children('li').focusin(function(event) {
		// Hide all child ul's first
		hideElement($(fourthlevel).children('li').children('ul'));
		
		// gather width and height values of li item
		widthOffset = adjustPlacement($(this), Number($(this).width()));
		heightOffset = Number($(this).height());
		showElement($(this).children('ul'), widthOffset, 0, false); // zero out the height offset - want on the same line
		// dim third level links
		$(thirdlevel).children('li').each(function(index, element) {
			$(this).children('a').addClass('dimText');
		});
		$(this).parent('ul').parent('li').children('a').removeClass('dimText');
		$(this).parent('ul').parent('li').children('a').removeClass('rightarrow');
		$(this).parent('ul').parent('li').children('a').addClass('right3arrowhover');
		
	});
	// mouseout on third level li's 
	$(fourthlevel).children('li').mouseleave(function(event) {
		// Hide all child ul's
		hideElement($(fourthlevel).children('li').children('ul'));
		
		// undo dim second level links
		$(thirdlevel).children('li').each(function(index, element) {
			$(this).children('a').removeClass('dimText');
		});
		$(this).parent('ul').parent('li').children('a').removeClass('right3arrowhover');
		$(this).parent('ul').parent('li').children('a').addClass('rightarrow')
	});
	$(fourthlevel).children('li').focusout(function(event) {
		// dim third level links
		$(thirdlevel).children('li').each(function(index, element) {
			$(this).children('a').removeClass('dimText');
		});
		$(this).parent('ul').parent('li').children('a').removeClass('right3arrowhover');
		$(this).parent('ul').parent('li').children('a').addClass('rightarrow')
	});
	
	
	// ############ LEVEL FIVE
	// ############ hide and show sixth level
	// show sixth level items on mouseover
	$(fifthlevel).children('li').mouseenter(function(event) {
		widthOffset = adjustPlacement($(this), Number($(this).width()));
		heightOffset = Number($(this).height());
		showElement($(this).children('ul'), widthOffset, 0, true);  // zero out the height offset - want on the same line
		// dim fourth level links
		$(fourthlevel).children('li').each(function(index, element) {
			$(this).children('a').addClass('dimText');
		});
		$(this).parent('ul').parent('li').children('a').removeClass('dimText');
		$(this).parent('ul').parent('li').children('a').removeClass('rightarrow');
		$(this).parent('ul').parent('li').children('a').addClass('right4arrowhover');
	});
	// keyboard tab in through fourth level
	$(fifthlevel).children('li').focusin(function(event) {
		// Hide all child ul's first
		hideElement($(fifthlevel).children('li').children('ul'));
		
		// gather width and height values of li item
		widthOffset = adjustPlacement($(this), Number($(this).width()));
		heightOffset = Number($(this).height());
		showElement($(this).children('ul'), widthOffset, 0, false); // zero out the height offset - want on the same line
		// dim fourth level links
		$(fourthlevel).children('li').each(function(index, element) {
			$(this).children('a').addClass('dimText');
		});
		$(this).parent('ul').parent('li').children('a').removeClass('dimText');
		$(this).parent('ul').parent('li').children('a').removeClass('rightarrow');
		$(this).parent('ul').parent('li').children('a').addClass('right4arrowhover');
		
	});
	// mouseout on third level li's 
	$(fifthlevel).children('li').mouseleave(function(event) {
		// Hide all child ul's
		hideElement($(fifthlevel).children('li').children('ul'));
		// undo dim fourth level links
		$(fourthlevel).children('li').each(function(index, element) {
			$(this).children('a').removeClass('dimText');
		});
		$(this).parent('ul').parent('li').children('a').removeClass('right4arrowhover');
		$(this).parent('ul').parent('li').children('a').addClass('rightarrow')
	});
	$(fifthlevel).children('li').focusout(function(event) {
		// dim fourth level links
		$(fourthlevel).children('li').each(function(index, element) {
			$(this).children('a').removeClass('dimText');
		});
		$(this).parent('ul').parent('li').children('a').removeClass('right4arrowhover');
		$(this).parent('ul').parent('li').children('a').addClass('rightarrow')
	});
	
});

// this function adjusts the placement of the sub-menu based on parent location
function adjustPlacement(elem, widthOffset) {
	documentWidth = Number($(document).width());  // get page width
	parentWidth = Number($(elem).width());  // get the width of the parent list
	parentPosition = Number($(elem).offset().left);
	childWidth = Number($(elem).children('ul').width());  // get the width of the child list
	rightEdgePosition = Number(parentPosition + parentWidth + childWidth); // get the right position of the element
	if (rightEdgePosition > documentWidth) {
		if ((parentPosition < childWidth) && (parentPosition > 0)) {
			difference = Number(childWidth-parentPosition);
			return 0-parentWidth + difference;
		}
		else {
			return 0-parentWidth+5;
		}
	}
	
	else {
		return parentWidth-5; // no need to adjust the offset
	}
};

// moves the child element into position
function showElement(elem, width, height, fade) {
	if ($('html').hasClass('gt-ie9')) $(elem).hide(); // IE doesn't like hide/show for keyboard accessibility - stupid browser
	$(elem).css('left', width);
	$(elem).css('top', height);
	if (fade == true) { 
		if ($('html').hasClass('gt-ie9')) $(elem).fadeIn(300); // IE doesn't like hide/show for keyboard accessibility - stupid browser
	} else {
		if ($('html').hasClass('gt-ie9')) $(elem).show(); // IE doesn't like hide/show for keyboard accessibility - stupid browser
	}
}
// moves the child element out of the document area
function hideElement(elem) {
	$(elem).css('left', '-5000px');
	$(elem).css('top', 0);
	if ($('html').hasClass('gt-ie9')) $(elem).show();  // IE doesn't like hide/show for keyboard accessibility - stupid browser
}