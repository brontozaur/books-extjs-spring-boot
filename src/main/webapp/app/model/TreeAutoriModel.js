Ext.define('BM.model.TreeAutoriModel', {
            extend: 'Ext.data.Model',
            fields: [
                'leaf',
                'name',
                'howManyAutors',
                'howManyBooks',
                {
                    name: 'treeItemName',
                    type: 'string',
                    convert: function(newValue, record) {
                    	if (record.get('id') === 'root'){
                    		return record.get('name');
                    	}
                        var howManyBooks = record.get('howManyBooks');
                        if (Ext.isEmpty(record.get('leaf')) || record.get('leaf') === false) {
                            var letter = record.get('name');
                            var howManyAutors = record.get('howManyAutors');
                            var autor = howManyAutors === 1 ? 'autor' : 'autori';
                            var carte = howManyBooks === 1 ? 'carte' : 'carti';

                            return letter + ' (' + howManyAutors + ' ' + autor + ', ' + howManyBooks + ' ' + carte + ')';
                        }
                        return record.get('name') + ' (' + howManyBooks + ')';
                    }
                }
            ]
        });