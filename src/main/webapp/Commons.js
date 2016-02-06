var booksPerPage = 10;
var edituriPerPage = 10;
var categoriiPerPage = 10;
var autoriPerPage = 10;
/**
 * This is an ugly hack. In order to keep grid pagination and also to
 * display all items in combo (so far, no pagination is needed in combo boxes),
 * i have extended the original store, overriding the pageSize property,
 * crossing my fingers that there will never be more that <b>pageSizeMax</b>
 * objects of that type (e.g. authors). To whoever use this code: i am sorry :-)
 * @type {number}
 */
var pageSizeMax = 999999999;

function enableBookGridButtons(enable) {
    var modButton = Ext.ComponentQuery.query('booksgrid button[action=mod-book]')[0];
    var delButton = Ext.ComponentQuery.query('booksgrid button[action=del-book]')[0];
    if (enable) {
        modButton.enable();
        delButton.enable();
    } else {
        modButton.disable();
        delButton.disable();
    }
    var grid = Ext.ComponentQuery.query('booksgrid')[0];
    if (grid.booksMenu) {
        grid.booksMenu.items.get('modBook').setDisabled(!enable);
        grid.booksMenu.items.get('delBook').setDisabled(!enable);
    }
}

function fillInfoArea (record) {
    var bookInfo = Ext.ComponentQuery.query('bookinfo')[0];
    var fieldContainer = bookInfo.down('container[itemId=bookInfoFields]');

    var autorField = fieldContainer.down('displayfield[itemId=autor]');
    autorField.setValue(record.get('authorName'));
    autorField.setVisible(!Ext.isEmpty(autorField.getValue()));

    var titleField = fieldContainer.down('displayfield[itemId=title]');
    titleField.setValue(record.get('title'));
    titleField.setVisible(!Ext.isEmpty(titleField.getValue()));

    var anField = fieldContainer.down('displayfield[itemId=data]');
    anField.setValue(record.get('anAparitie'));
    anField.setVisible(!Ext.isEmpty(anField.getValue()));

    var originalTitleField = fieldContainer.down('displayfield[itemId=originalTitle]');
    originalTitleField.setValue(record.get('originalTitle'));
    originalTitleField.setVisible(!Ext.isEmpty(originalTitleField.getValue()));

    var isbnField = fieldContainer.down('displayfield[itemId=isbn]');
    isbnField.setValue(record.get('isbn'));
    isbnField.setVisible(!Ext.isEmpty(isbnField.getValue()));

    var cititaField = fieldContainer.down('displayfield[itemId=citita]');
    cititaField.setValue(record.get('citita') ? "Da" : "Nu");
    cititaField.setVisible(true);

    var serieField = fieldContainer.down('displayfield[itemId=serie]');
    serieField.setValue(record.get('serie'));
    serieField.setVisible(!Ext.isEmpty(serieField.getValue()));

    var nrPaginiField = fieldContainer.down('displayfield[itemId=nrPagini]');
    nrPaginiField.setValue(record.get('nrPagini'));
    nrPaginiField.setVisible(nrPaginiField.getValue() > 0);

    var dimensiuniField = fieldContainer.down('displayfield[itemId=dimensiuni]');
    if (record.get('width') > 0 || record.get('height') > 0) {
        dimensiuniField.setValue(record.get('width') + ' x ' + record.get('height'));
    } else {
        dimensiuniField.setValue('');
    }
    dimensiuniField.setVisible(!Ext.isEmpty(dimensiuniField.getValue()));

    var edituraField = fieldContainer.down('displayfield[itemId=numeEditura]');
    edituraField.setValue(record.get('numeEditura'));
    edituraField.setVisible(!Ext.isEmpty(edituraField.getValue()));

    var genField = fieldContainer.down('displayfield[itemId=numeCategorie]');
    genField.setValue(record.get('numeCategorie'));
    genField.setVisible(!Ext.isEmpty(genField.getValue()));

    var frontImageContainer = bookInfo.down('image[itemId=frontCoverInfo]');
    var hasFrontCover = !Ext.isEmpty(record.get('frontCover'));
    bookInfo.down('label[itemId=frontCoverLabel]').setVisible(hasFrontCover);
    frontImageContainer.setVisible(hasFrontCover);
    frontImageContainer.setSrc('data:image/jpeg;base64,' + record.get('frontCover'));

    var backImageContainer = bookInfo.down('image[itemId=backCoverInfo]');
    var hasBackCover = !Ext.isEmpty(record.get('backCover'));
    bookInfo.down('label[itemId=backCoverLabel]').setVisible(hasBackCover);
    backImageContainer.setVisible(hasBackCover);
    backImageContainer.setSrc('data:image/jpeg;base64,' + record.get('backCover'));
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

function createGenericErrorWindow(dataArray) {
    var window = Ext.widget('errorwindow');
    if (dataArray['windowTitle']) {
        window.setTitle(dataArray['windowTitle']);
    } else {
        window.setTitle('A intervenit o eroare');
    }

    if (dataArray['errorMessage']) {
        window.setErrorMessage("Eroare: " + dataArray['errorMessage']);
    } else {
        window.setErrorMessage('A intervenit o eroare');
    }

    var details = 'Details:';
    for (var key in dataArray) {
        if (key !== 'windowTitle' && key !== 'errorMessage') {
            details += '\n\t' + key + ': ' + dataArray[key];
        }
    }
    window.setErrorDetails(details);
    window.show();
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
        console.log("unable to decode the response" + e);
        parsedResponse = Ext.JSON.decode(JSON.stringify(response.responseText));
    }
    fillErrorDetailsOnWindow(window, parsedResponse);
    window.show();
}

function fillErrorDetailsOnWindow(window, parsedResponse) {
    window.setTitle('A intervenit o eroare!');
    var errorMessage;
    if (parsedResponse.message) {
        errorMessage = parsedResponse.message;
    }
    if (!Ext.isEmpty(errorMessage)) {
        window.setErrorMessage("Eroare: " + errorMessage);
        var details = 'Detalii:' +
            '\n-----------------'+
            '\n\t Mesaj: '+ parsedResponse.message +
            '\n\t Data si ora: '+ Ext.Date.format(parsedResponse.timeStamp) +
            '\n\t URL: '+ parsedResponse.url;
        if (!Ext.isEmpty(parsedResponse.parameters)) {
            var params = parsedResponse.parameters.split("$$");
            details += '\n\t Parameters: ';
            for (var i = 0; i<params.length; i++) {
                details += '\n\t\t' + params[i];
            }
        } else {
            details += '\n\t Parameters: This request had no parameters';
        }

       details +=
            '\n\t HTTP method: '+ parsedResponse.method +
            '\n\t Status: '+ parsedResponse.status + ' (' + parsedResponse.statusDecoded + ')' +
            '\n\t Accept: '+ parsedResponse.accept +
            '\n\t Server info: '+ parsedResponse.serverInfo +
            '\n\t Cale reala: '+ parsedResponse.realPath +
            '\n\t User agent: '+ parsedResponse.userAgent +
            '\n\t Protocol: '+ parsedResponse.protocol;
        if (!Ext.isEmpty(parsedResponse.trace)) {
            details +=
                '\n\n Stracktrace:'+
                '\n-----------------'+
                '\n' + parsedResponse.trace.split("		");
        }
        window.setErrorDetails(details);
    } else {
        window.setErrorMessage('A intervenit o eroare!');
        window.setErrorDetails(parsedResponse);
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

function enableAutorGridButtons(enable) {
    var modButton = Ext.ComponentQuery.query('autorgrid button[action=mod-autor]')[0];
    var delButton = Ext.ComponentQuery.query('autorgrid button[action=del-autor]')[0];
    if (enable) {
        modButton.enable();
        delButton.enable();
    } else {
        modButton.disable();
        delButton.disable();
    }
    var grid = Ext.ComponentQuery.query('autorgrid')[0];
    if (grid.autorMenu) {
        grid.autorMenu.items.get('modAutor').setDisabled(!enable);
        grid.autorMenu.items.get('delAutor').setDisabled(!enable);
    }
}

function enableEdituraGridButtons(enable) {
    var modButton = Ext.ComponentQuery.query('edituragrid button[action=mod-editura]')[0];
    var delButton = Ext.ComponentQuery.query('edituragrid button[action=del-editura]')[0];
    if (enable) {
        modButton.enable();
        delButton.enable();
    } else {
        modButton.disable();
        delButton.disable();
    }
    var grid = Ext.ComponentQuery.query('edituragrid')[0];
    if (grid.edituraMenu) {
        grid.edituraMenu.items.get('modEditura').setDisabled(!enable);
        grid.edituraMenu.items.get('delEditura').setDisabled(!enable);
    }
}

function enableCategorieGridButtons(enable) {
    var modButton = Ext.ComponentQuery.query('categoriegrid button[action=mod-categorie]')[0];
    var delButton = Ext.ComponentQuery.query('categoriegrid button[action=del-categorie]')[0];
    if (enable) {
        modButton.enable();
        delButton.enable();
    } else {
        modButton.disable();
        delButton.disable();
    }
    var grid = Ext.ComponentQuery.query('categoriegrid')[0];
    if (grid.categorieMenu) {
        grid.categorieMenu.items.get('modCategorie').setDisabled(!enable);
        grid.categorieMenu.items.get('delCategorie').setDisabled(!enable);
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
        document.getElementById(theme).disabled = true;
    }
    document.getElementById(themeName).disabled = false;
}

function appendBooksStoreExtraParameters(store) {
    if (!store.pageSize) { //no parameters are appended for combo stores, which should not pe paged
        return;
    }
    store.proxy.extraParams.filterValue = store.filterValue;
    store.proxy.extraParams.searchType = store.searchType;
}