Ext.define('BM.view.autor.AutorCombo', {
            extend: 'Ext.form.field.ComboBox',
            xtype: 'autorCombo',
            model: 'BM.model.AutorModel',
            displayField: 'nume',
            valueField: 'autorId',
            store: 'AutorStore',
            queryMode: 'local',
            listeners: {
                render: function(combo, options) {
                    combo.getStore().load();
                }
            }
        });