/**
 * @author Edward H. Seufert
 * Copyright (C) 2016 The ToastHub Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

publicLayout.prototype = Object.create(toastHubPublicLayout.prototype);
publicLayout.prototype.constructor = publicLayout;

toastHub.registerLayout("publicLayout",new publicLayout("publicLayout"));

function publicLayout(instanceName){
	toastHubPublicLayout.call(this,instanceName,this);
	this.controllerName = "publicLayout";
	var self = this;
	
	// Override the default setup
	this.navRendererDraw = function(JSONData) {
		toastHub.logSystem.log("DEBUG","toastHub-publicLayout::toastHubPublicLayout::navRendererDraw");
		var menuRight = JSONData.params.MENUS.PUBLIC_MENU_RIGHT;
		
		var navWrap = document.createElement("DIV");
		navWrap.className = "container";
		toastHub.containerMenuObj.appendChild(navWrap);
		
		var navHeader = document.createElement("DIV");
		navHeader.className = "navbar-header page-scroll";
		navWrap.appendChild(navHeader);
		var buttonToggle = document.createElement("BUTTON");
		buttonToggle.type = "button";
		buttonToggle.className = "navbar-toggle";
		buttonToggle.setAttribute("data-toggle", "collapse");
		buttonToggle.setAttribute("data-target", "#bs-example-navbar-collapse-1");
		
		buttonToggle.innerHTML = "<span class='sr-only'>Toggle navigation</span> Menu <i class='fa fa-bars'></i>"
		navHeader.appendChild(buttonToggle);
		var logo = document.createElement("A");
		logo.className = "navbar-brand page-scroll";
		logo.href = "#page-top";
		logo.innerHTML = "CBORGTECH";
		navHeader.appendChild(logo);
		
		
		var menu = document.createElement("DIV");
		menu.className = "collapse navbar-collapse";
		menu.id = "bs-example-navbar-collapse-1";
		navWrap.appendChild(menu);
		var menuUL = document.createElement("UL");
		menuUL.className = "nav navbar-nav navbar-right";
		menu.appendChild(menuUL);
		
		for (var i in menuRight){
			if (menuRight.hasOwnProperty(i)){
				var menuLI = document.createElement("li");
				menuUL.appendChild(menuLI);
				var menuHref = document.createElement("a");
				menuHref.className = 'page-scroll';
				
				menuHref.href = menuRight[i].values[0].href;
				menuHref.innerHTML = menuRight[i].values[0].value;
				menuLI.appendChild(menuHref);
				if (menuRight[i].children != null){
					var menuSub = document.createElement("ul");
					menuSub.className = "sub";
					this.addSubMenu(menuSub,menuRight[i].children);
					menuLI.appendChild(menuSub);
				}
			}
		}

	}; // navRendererDraw
	
}; // publicLayout






