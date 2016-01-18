Ext.define('BM.model.TreeEdituraModel', {
            extend: 'Ext.data.Model',
            fields: [
                'leaf',
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
                        if (Ext.isEmpty(record.get('leaf')) || record.get('leaf') === false) {
                            var letter = record.get('name');
                            var howManyEdituri = record.get('howManyEdituri');
                            var editura = howManyEdituri === 1 ? 'editura' : 'edituri';
                            var carte = howManyBooks === 1 ? 'carte' : 'carti';

                            return letter + ' (' + howManyEdituri + ' ' + editura + ', ' + howManyBooks + ' ' + carte + ')';
                        }
                        return record.get('name') + ' (' + howManyBooks + ')';
                    }
                }
            ]
        });