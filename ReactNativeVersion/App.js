import React from 'react';
import { StyleSheet, Text, View, ListView, StatusBar, Button, TextInput, Linking } from 'react-native';
import { StackNavigator } from 'react-navigation';

memo_list = [
    {
        title: 'Feed the unicorn',
        memo: ' with rainbow skittles',
        key: 'item0'
    },
    {
        title: 'Pet the cat',
        memo: 'vinerea pe la spa',
        key: 'item1'
    },
    {
        title: 'Keep calm',
        memo: 'and carry on',
        key: 'item2'
    },
    {
        title: 'Set new alarm',
        memo: 'at 5 o\'clock',
        key: 'item3'
    },
    {
        title: 'Read the news',
        memo: 'just to seem cool',
        key: 'item4'
    },
    {
        title: 'Drink water',
        memo: '5 oz',
        key: 'item5'
    },
    {
        title: 'I know',
        memo: 'there\'s gonna be good times',
        key: 'item6'
    },
    {
        title: 'Good timez',
        memo: 'there\'s gon\' be some good times',
        key: 'item7'
    },
]

function getItemWithId(key) {
    for (i = 0; i < memo_list.length; ++i) {
        if (memo_list[i].key === key) {
            return memo_list[i];
        }
    }
    return {};
}
function updateItem(newItem) {
    for (i = 0; i < memo_list.length; ++i) {
        if (memo_list[i].key == newItem.key) {

            memo_list[i].title = newItem.title;
            memo_list[i].memo = newItem.memo;
        }
    }
}

class HomeScreen extends React.Component {

    static navigationOptions = {
        title: 'Home - View Memos',
    }

    constructor(){
        super();
        const dataSource = new ListView.DataSource({rowHasChanged: (r1, r2) => r1 != r2});
        this.state = {
            dataSource: dataSource.cloneWithRows(memo_list),
        }
    }

  render() {
    return (
      <View style={styles.containerMain}>
          <StatusBar hidden/>
          <ListView
              dataSource={this.state.dataSource}
              renderRow={(rowData) => <CardView data={rowData} navigation={this.props.navigation} />}
          />
      </View>
    );
  }
}

class CardView extends React.Component {

    handleOnNavigateBack = () => {
        this.forceUpdate();
    }

    render() {
        //const { navigate } = this.props.navigation;
        return (
            <View style={styles.containerElement}>
                <Text style = {styles.boldText}>{"Title:"}</Text>
                <Text>{this.props.data.title}</Text>
                <Text style = {styles.boldText}>{'Memo:'}</Text>
                <Text>{this.props.data.memo}</Text>
                <Button
                    title='Edit Memo'
                    onPress={() => this.props.navigation.navigate('EditMemo', { data: this.props.data.key,
                        onNavigateBack: this.handleOnNavigateBack})}//, navigation: this.props.navigation })}

                />
            </View>
        )
    }
}

class EditMemoScreen extends React.Component {
    static navigationOptions = {
        title: 'Edit Selected Memo',
    }
    constructor(props) {
        super(props);
        const { params } = this.props.navigation.state;
        this.state = getItemWithId(params.data);
        //console.log(this.state);
    }
    render() {
        return (
            <View style={styles.containerDialog}>

                <StatusBar hidden/>

                <Text style = {styles.boldText}>{"Insert Memo Title:"}</Text>
                <TextInput
                    onChangeText={(title) => this.setState({title: title})}
                    value={this.state.title}
                />

                <Text style = {styles.boldText}>{"Insert Memo Text:"}</Text>
                <TextInput
                    onChangeText={(memo) => this.setState({memo: memo})}// title:memo
                    value={this.state.memo}
                />

                <Button
                    title='Save Memo'
                    onPress={() =>{
                        updateItem(this.state);
                        this.props.navigation.state.params.onNavigateBack();
                        Linking.openURL(`mailto:sebastian.s.ciuca@gmail.com?subject=MemoUpdated`);}}
                        //this.props.navigation.dispatch(NavigationActions.back())}
                    />
            </View>
        )
    }
}

const styles = StyleSheet.create({
    containerMain: {
        flex: 1,
        backgroundColor: '#fff',
        alignItems: 'center',
        justifyContent: 'center',
        marginTop:  20,
        padding: 5,
        marginBottom: 20,
    },
    containerElement : {
        backgroundColor: '#614051',
        alignItems: 'center',
        marginTop:  10,
        padding: 10,
    },
    containerDialog : {
        flex: 1,
        backgroundColor: '#1ABC9C',
        alignItems: 'center',
        marginTop:  10,
        padding: 10,
    },
    boldText: {
      fontWeight: 'bold',
      fontSize: 16,
    }
});

export default StackNavigator({
    Home: { screen: HomeScreen },
    EditMemo: { screen: EditMemoScreen },
});