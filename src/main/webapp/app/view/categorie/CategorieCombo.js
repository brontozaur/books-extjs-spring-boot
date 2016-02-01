Ext.define('BM.view.categorie.CategorieCombo', {
    extend: 'Ext.form.field.ComboBox',
    xtype: 'categorieCombo',
    model: 'BM.model.CategorieModel',
    displayField: 'numeCategorie',
    valueField: 'idCategorie',
    queryMode: 'local',
    store: 'CategorieStore',
    listeners: {
        render: function (combo, options) {
            combo.getStore().load();
        }
    }
});