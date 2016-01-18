Ext.define('BM.model.TreeBooksModel', {
            extend: 'Ext.data.Model',
            fields: [
                'leaf',
                'name',
                'howManyBooks',
                {
                    name: 'treeItemName',
                    type: 'string',
                    convert: function(newValue, record) {
                        if (record.get('id') === 'root') {
                            return record.get('name');
                        }
                        var howManyBooks = record.get('howManyBooks');
                        var letter = record.get('name');
                        var carte = howManyBooks === 1 ? 'carte' : 'carti';

                        return letter + ' (' + howManyBooks + ' ' + carte + ')';
                    }
                }
            ]
        });