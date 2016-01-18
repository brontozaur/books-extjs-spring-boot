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

function createFormErrorWindow(formAction) {
    var window = Ext.widget('errorwindow');
    var errorStackTrace = formAction.response.responseText;
    window.setTitle('Mesaj de eroare');
    window.setErrorMessage('A intervenit o eroare!');
    window.setStackTace('Detalii:\n-----------------\n' + errorStackTrace);
    window.show();
}

function createErrorWindow(response) {
    var window = Ext.widget('errorwindow');
    var errorMessage = response.getAllResponseHeaders().error_message;
    var errorRootCasue = response.getAllResponseHeaders().error_root_cause;
    var errorStackTrace = response.getAllResponseHeaders().error_stacktrace;
    window.setTitle('Mesaj de eroare');
    if (!Ext.isEmpty(errorMessage)) {
        window.setErrorMessage(errorMessage);
    } else {
        window.setErrorMessage('A intervenit o eroare!');
    }
    window.setStackTace('Cauza:\n-----------------\n' + errorRootCasue + '\n\nDetalii:\n-----------------\n' + errorStackTrace);
    window.show();
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