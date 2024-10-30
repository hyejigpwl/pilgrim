(function($) {
	skel.breakpoints({
			xlarge:	'(max-width: 1680px)',
			large:	'(max-width: 1280px)',
			medium:	'(max-width: 980px)',
			small:	'(max-width: 736px)',
			xsmall:	'(max-width: 480px)'
		});

		$(function() {

			var	$window = $(window),
				$body = $('body');

			// Disable animations/transitions until the page has loaded.
				$body.addClass('is-loading');

				$window.on('load', function() {
					window.setTimeout(function() {
						$body.removeClass('is-loading');
					}, 100);
				});

			// Fix: Placeholder polyfill.
				$('form').placeholder();

			// Prioritize "important" elements on medium.
				skel.on('+medium -medium', function() {
					$.prioritize(
						'.important\\28 medium\\29',
						skel.breakpoint('medium').active
					);
				});

			// Off-Canvas Navigation.

				// Navigation Panel Toggle.
					$('<a href="#navPanel" class="navPanelToggle"></a>')
						.appendTo($body);

				// Navigation Panel.
					$(
						'<div id="navPanel">' +
							$('#nav').html() +
							'<a href="#navPanel" class="close"></a>' +
						'</div>'
					)
						.appendTo($body)
						.panel({
							delay: 500,
							hideOnClick: false,
							hideOnSwipe: true,
							resetScroll: true,
							resetForms: true,
							side: 'right'
						});

				// Fix: Remove transitions on WP<10 (poor/buggy performance).
					if (skel.vars.os == 'wp' && skel.vars.osVersion < 10)
						$('#navPanel')
							.css('transition', 'none');
							
							
					// gnb 메뉴 슬라이드
															
					$("#nav>ul>li").mouseover(function(){
		                $("#nav .submenu").stop().hide();
		                $(this).find("ul").stop().show();
		                $("#nav>ul>li").children("a").css({
		                    "color":"#333"
		                });
						$("#nav>ul>li>a.point").css({"color":"#a07c1f"});
		            });
					
					$("#header.alt").mouseleave(function(){
						$(this).css({"background-color":"transparent"});
						$("#nav>ul>li>a").css({"color":"#ddd"});
						$("#nav>ul>li>a.point").css({"color":"#fff"});
					});
					
		            $("#nav .submenu,#nav>ul>li").mouseleave(function(){
		                $("#nav .submenu").stop().hide();
		            });
					
					

		            // 모바일 gnb 아코디언
					$("#navPanel ul li").click(function(){
						
					    // 슬라이드
					    $("#navPanel .submenu").slideUp();
					    $(this).children("a").siblings().stop().slideToggle();
						$("#navPanel").addClass("visible");
						
						
					    // 아이콘 전환
					    // $(this).children().children().toggleClass("selected");
					    // $(".arrow_icon").not( $(this).children().children()).removeClass("selected");
					    
					});	
		            
					
					// aos
					AOS.init();

		});
					
				
})