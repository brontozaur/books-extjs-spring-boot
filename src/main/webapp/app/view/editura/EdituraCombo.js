Ext.define('BM.view.editura.EdituraCombo', {
            extend: 'Ext.form.field.ComboBox',
            xtype: 'edituraCombo',
            model: 'BM.model.EdituraModel',
            displayField: 'numeEditura',
            valueField: 'idEditura',
            store: 'EdituraStore',
            queryMode: 'local',
            listeners: {
                render: function(combo, options) {
                    combo.getStore().load();
                }
            }
        });