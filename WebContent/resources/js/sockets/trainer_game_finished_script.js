var sum = 1000; // rank 1.
var time = 250;
var initializedPodium = false;
$(document).ready(function() {
  

  
function initPodium() {
	$('body').addClass('dark-mode');
	  
	$('.js-style-switch').on('click', function(e) {
	  e.preventDefault();
	  $('body').toggleClass('dark-mode');
	});
	  
	$('.js-podium').each(function(){
	  var t = $(this);
	  setTimeout( function(){ 
	  t.addClass('is-visible');
	  var h = t.data('height');
	  console.log(h);
	  t.find('.scoreboard__podium-base').css('height', h).addClass('is-expanding');
	    }, time);
	   time += 250;
	});
   generateData();
   startBars();
   sortItems();
   countUp();
  // randomizePodium();
}
   
  
  setInterval(function(){ 
	  console.log("interval...");
	//Make sure that the podium is displaying
	if (displayingQuestion) {
		subtractSecond();
	}
	if (displayPodium) {
		if (!initializedPodium) {
			initPodium();
			initializedPodium = true;
		}
		console.log("display podium");
	    startBars();
	    sortItems();
	    countUp();
	    oneUp();
	    $('.js-podium').removeClass('bump');
	}
  }, 1000);
}); 


// Randomize function to produce dummy data
function generateData() {	

    $('.scoreboard__item').each(function(){  
	var val = document.getElementById("Score").innerHTML ;
	$(this).data('count', val);
 });
}


function startBars() {
 $('.js-bar').each(function() {
   console.log('running');
  // calculate %.
  var t = $(this);
   setTimeout( function(){ 
  var width = t.parent('.scoreboard__item').data('count'); 
  width = width  / sum * 100;
     width = Math.round(width);
  t.find('.scoreboard__bar-bar').css('width',  width + "%");
     t.parent('li').addClass('is-visible');
      }, time);
   time += 0;
  });
}

function sortItems() {
 tinysort.defaults.order = 'desc';
  
 var ul = document.getElementById('scoreboard__items')
    ,lis = ul.querySelectorAll('li')
    ,liHeight = lis[0].offsetHeight
;
ul.style.height = ul.offsetHeight+'px';
for (var i= 0,l=lis.length;i<l;i++) {
    var li = lis[i];
    li.style.position = 'absolute';
    li.style.top = i*liHeight+'px';
}
tinysort('ol#scoreboard__items>li', 'span.js-number').forEach(function(elm,i){
    setTimeout((function(elm,i){
        elm.style.top = i*liHeight+'px';
    }).bind(null,elm,i),40);
});
  
  
}

function randEmail() {
  var chars = 'abcdefghijklmnopqrstuvwxyz1234567890';
var string = '';
for(var ii=0; ii<15; ii++){
    string += chars[Math.floor(Math.random() * chars.length)];
}
  return string + '@domain.tld';
}

function oneUp() {
  // play audio - update email.
  var randEmail = window.randEmail();
  $('.js-oneup').html('Ny påmeldt: ' + randEmail);
  $('.js-oneup-audio')[0].play();
}
