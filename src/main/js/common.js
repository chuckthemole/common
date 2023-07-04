// Paths
export const API_ROOT = '/api';
export const ROOT = '/';

// Keywords
export const EMPTY = '';
export const USERNAME = 'username';
export const USER = 'user';
export const ID = 'id';
export const PASSWORD = 'password';
export const EMAIL = 'email';

// Crud
export const POST = 'POST';
export const GET = 'GET';

// * * * * * * * * * * *
// * * * Functions * * *
// * * * * * * * * * * * 

// Modals
export function ActivateModal(modal, button) {
    if (button !== undefined) {
        button.onclick = function () {
            AddActiveClipped(modal);
        }
    }
    RemoveActiveClippedOnClick(modal);
    CloseOnBackgroundClick('modal-background', 'modal');
}

// Activates modal without button
export function ActivateModalNoButton(modal) {
    AddActiveClipped(modal);
    RemoveActiveClippedOnClick(modal);
    CloseOnBackgroundClick('modal-background', 'modal');
}

function AddActiveClipped(modal) {
    modal.classList.add("is-active");
    modal.classList.add("is-clipped");
}
function RemoveActiveClipped(modal) {
    modal.classList.remove("is-active");
    modal.classList.remove("is-clipped");
}
function RemoveActiveClippedOnClick(modal) {
    if (modal.getElementsByClassName("modal-close")[0] !== undefined) {
        modal.getElementsByClassName("modal-close")[0].onclick = function () {
            RemoveActiveClipped(modal);
        }
    }
}
function CloseOnBackgroundClick(backgroundClass, modalClass) {
    // If click outside the modal, close modal
    window.onclick = function (event) {
        var backgrounds = document.getElementsByClassName(backgroundClass);
        Array.prototype.forEach.call(backgrounds, function (background) { // get all modal backgrounds
            if (event.target == background) {
                var modals = document.getElementsByClassName(modalClass);
                Array.prototype.forEach.call(modals, function (modal) { // remove is-active from all modals
                    RemoveActiveClipped(modal);
                });
            }
        });
    }
}

export function GetClientTimeZoneOffset() {
    const globalDate = new Date();
    let diffZone = globalDate.getTimezoneOffset();
    console.log("offset: " + diffZone);
}

export function ConvertEpochToDate(epochTime) {
    return new Date(parseInt(epochTime));
}