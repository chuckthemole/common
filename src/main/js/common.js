// Paths
export const API_ROOT = '/api';
export const ROOT = '/';

// Keywords
export const EMPTY = '';
export const USERNAME = 'username';
export const PASSWORD = 'password';
export const EMAIL = 'email';

// Crud
export const POST = 'POST';
export const GET = 'GET';

// Functions
export function ActivateModal(modal, button) {
    // For each modal, add click event
   // on button click add is-active, on x click remove is-active
   if (button !== undefined) {
       button.onclick = function () {
           modal.classList.add("is-active");
           modal.classList.add("is-clipped");
       }
   }
   if (modal.getElementsByClassName("modal-close")[0] !== undefined) {
       modal.getElementsByClassName("modal-close")[0].onclick = function () {
           modal.classList.remove("is-active");
           modal.classList.remove("is-clipped");
       }
   }

   // If click outside the modal, close modal
   window.onclick = function (event) {
       var backgrounds = document.getElementsByClassName("modal-background");
       Array.prototype.forEach.call(backgrounds, function (background) { // get all modal backgrounds
           if (event.target == background) {
               var modals = document.getElementsByClassName("modal");
               Array.prototype.forEach.call(modals, function (modal) { // remove is-active from all modals
                   modal.classList.remove("is-active");
                   modal.classList.remove("is-clipped");
               });
           }
       });
   }
}

// Activates modal without button
export function ActivateModalNoButton(modal) {
    modal.classList.add("is-active");
    modal.classList.add("is-clipped");
   if (modal.getElementsByClassName("modal-close")[0] !== undefined) {
       modal.getElementsByClassName("modal-close")[0].onclick = function () {
           modal.classList.remove("is-active");
           modal.classList.remove("is-clipped");
       }
   }

   // If click outside the modal, close modal
   window.onclick = function (event) {
       var backgrounds = document.getElementsByClassName("modal-background");
       Array.prototype.forEach.call(backgrounds, function (background) { // get all modal backgrounds
           if (event.target == background) {
               var modals = document.getElementsByClassName("modal");
               Array.prototype.forEach.call(modals, function (modal) { // remove is-active from all modals
                   modal.classList.remove("is-active");
                   modal.classList.remove("is-clipped");
               });
           }
       });
   }
}