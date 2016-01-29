function enablebuttons(enable) {
    var modButton = Ext.ComponentQuery.query('booksgrid button[action=mod-book]')[0];
    var delButton = Ext.ComponentQuery.query('booksgrid button[action=del-book]')[0];
    if (enable) {
        modButton.enable();
        delButton.enable();
    } else {
        modButton.disable();
        delButton.disable();
    }
}

function clearInfoAreaFields() {
    var bookInfo = Ext.ComponentQuery.query('bookinfo')[0];

    var autorField = bookInfo.down('displayfield[itemId=autor]');
    autorField.setValue("");

    var titleField = bookInfo.down('displayfield[itemId=title]');
    titleField.setValue("");

    var dateField = bookInfo.down('displayfield[itemId=data]');
    dateField.setValue("");

    var originalTitleField = bookInfo.down('displayfield[itemId=originalTitle]');
    originalTitleField.setValue("");

    var isbnField = bookInfo.down('displayfield[itemId=isbn]');
    isbnField.setValue("");

    var cititaField = bookInfo.down('displayfield[itemId=citita]');
    cititaField.setValue("");

    var serieField = bookInfo.down('displayfield[itemId=serie]');
    serieField.setValue("");

    var nrPaginiField = bookInfo.down('displayfield[itemId=nrPagini]');
    nrPaginiField.setValue("");

    var dimensiuniField = bookInfo.down('displayfield[itemId=dimensiuni]');
    dimensiuniField.setValue("");

    var edituraField = bookInfo.down('displayfield[itemId=numeEditura]');
    edituraField.setValue("");

    var genField = bookInfo.down('displayfield[itemId=numeCategorie]');
    genField.setValue("");

    var frontCoverField = bookInfo.down('image[itemId=frontCoverInfo]');
    var frontLabel = bookInfo.down('label[itemId=frontCoverLabel]');
    frontCoverField.setSrc("");

    var backImageField = bookInfo.down('image[itemId=backCoverInfo]');
    var backLabel = bookInfo.down('label[itemId=backCoverLabel]');
    backImageField.setSrc("");

    autorField.setVisible(false);
    titleField.setVisible(false);
    dateField.setVisible(false);
    originalTitleField.setVisible(false);
    isbnField.setVisible(false);
    cititaField.setVisible(false);
    serieField.setVisible(false);
    nrPaginiField.setVisible(false);
    dimensiuniField.setVisible(false);
    edituraField.setVisible(false);
    genField.setVisible(false);
    frontCoverField.setVisible(false);
    frontLabel.setVisible(false);
    backImageField.setVisible(false);
    backLabel.setVisible(false);
}

/**
 * Be careful to send the correct argument to this function.
 * - for form.submit() request, send a action.response from failure(form, action) function
 * - for Ajax.requests, send the result of the failure(result, request) function
 * - for jQuery ajax calls, i didnt test it
 * @param response
 */
function createErrorWindow(response) {
    var window = Ext.widget('errorwindow');
    var parsedResponse;
    try {
        parsedResponse = Ext.JSON.decode(response.responseText);
    } catch(e){
        console.log("unable to decode the respoonse" + e);
        parsedResponse = Ext.JSON.decode(JSON.stringify(response.responseText));
    }
    fillErrorDetailsOnWindow(window, parsedResponse);
    window.show();
}

function fillErrorDetailsOnWindow(window, parsedResponse) {
    window.setTitle('A intervenit o eroare!');
    var errorMessage = parsedResponse.message;
    if (!Ext.isEmpty(errorMessage)) {
        window.setErrorMessage("Eroare: " + errorMessage);
        window.setStackTace('Detalii:' +
            '\n-----------------'+
            '\n\t Mesaj: '+ parsedResponse.message +
            '\n\t Data si ora: '+ Ext.Date.format(parsedResponse.timeStamp) +
            '\n\t URL: '+ parsedResponse.url +
            '\n\t Parameters: '+ parsedResponse.parameters +
            '\n\t HTTP method: '+ parsedResponse.method +
            '\n\t Status: '+ parsedResponse.status + ' (' + parsedResponse.statusDecoded + ')' +
            '\n\t Accept: '+ parsedResponse.accept +
            '\n\t Server info: '+ parsedResponse.serverInfo +
            '\n\t Cale reala: '+ parsedResponse.realPath +
            '\n\t User agent: '+ parsedResponse.userAgent +
            '\n\t Protocol: '+ parsedResponse.protocol +
            '\n\n Stracktrace:' +
            '\n-----------------'+
            '\n' + parsedResponse.trace);
    } else {
        window.setErrorMessage('A intervenit o eroare!');
        window.setStackTace(parsedResponse);
    }
}

function getFirstExpandedNode(root) {
    for (index in root.childNodes) {
        var child = root.childNodes[index];
        if (child.isExpanded()) {
            var expandedChild = getFirstExpandedNode(child);
            if (expandedChild) {
                return expandedChild;
            }
            return child;
        }
    }
}

function enablebuttonsAutor(enable) {
    var modButton = Ext.ComponentQuery.query('autorgrid button[action=mod-autor]')[0];
    var delButton = Ext.ComponentQuery.query('autorgrid button[action=del-autor]')[0];
    if (enable) {
        modButton.enable();
        delButton.enable();
    } else {
        modButton.disable();
        delButton.disable();
    }
}

function enablebuttonsEditura(enable) {
    var modButton = Ext.ComponentQuery.query('edituragrid button[action=mod-editura]')[0];
    var delButton = Ext.ComponentQuery.query('edituragrid button[action=del-editura]')[0];
    if (enable) {
        modButton.enable();
        delButton.enable();
    } else {
        modButton.disable();
        delButton.disable();
    }
}

function enablebuttonsCategorie(enable) {
    var modButton = Ext.ComponentQuery.query('categoriegrid button[action=mod-categorie]')[0];
    var delButton = Ext.ComponentQuery.query('categoriegrid button[action=del-categorie]')[0];
    if (enable) {
        modButton.enable();
        delButton.enable();
    } else {
        modButton.disable();
        delButton.disable();
    }
}

//this is how you can construct a singleton store, if required
function getCategorieStoreSingleton(){
    return Ext.StoreMgr.lookup('categorieStore') || Ext.create('BM.store.CategorieStore');
}

var themes = ['classic', 'neptune', 'gray'];
function setCurrentTheme(themeName){
    console.log('active theme is: ' + themeName);
    for (var idx in themes){
        var theme = themes[idx];
        document.getElementById(theme).disabled = true;;
    }
    document.getElementById(themeName).disabled = false;
}