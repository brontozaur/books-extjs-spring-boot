Ext.define('BM.model.TreeEdituraModel', {
            extend: 'Ext.data.Model',
            fields: [
                'name',
                'howManyEdituri',
                'howManyBooks',
                {
                    name: 'treeItemName',
                    type: 'string',
                    convert: function(newValue, record) {
                        if (record.get('id') === 'root') {
                            return record.get('name');
                        }
                        var howManyBooks = record.get('howManyBooks');
                        var carte = howManyBooks === 1 ? ' carte' : ' carti';
                        return record.get('name') + ' (' + howManyBooks + carte + ')';
                    }
                }
            ]
        });