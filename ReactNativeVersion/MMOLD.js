
import React from 'react';
import {
    StyleSheet, Text, View, FlatList, StatusBar, Alert, TextInput, TouchableOpacity,
    TouchableWithoutFeedback, Button
} from 'react-native';
import {NavigationScreenProp as navigate, StackNavigator} from 'react-navigation';

memos = [
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


function getItemWithId(id) {
    for (i = 0; i < memos.length; ++i) {
        if (memos[i].id === id) {
            return memos[i];
        }
    }
    return {};
}
function updateItem(newItem) {
    for (i = 0; i < memos.length; ++i) {
        if (memos[i].id == newItem.id) {
            memos[i].title = newItem.title;
            memos[i].memo = newItem.memo;
        }
    }
}


class EditItemScreen extends React.Component{
    static navigatorOptions = {
        title: "Edit Memo",
    }

    constructor(props){
        super();
        const {params} = this.props.navigation.state;
        this.state = getItemWithId(params.data);
        console.log(this.state);
    }

    render(){
        return(
            <View>
                <Text>'New title:'</Text>
                <TextInput
                    onChangeText={(title) => this.setState({title: title})}
                    value={this.state.title}
                />

                <Text>'New memo:'</Text>
                <TextInput
                    onChangeText={(memo) => this.setState({memo: memo})}
                    value={this.state.memo}
                />

                <Button
                    title='Save'
                    onPress={() => {updateItem(this.state);
                        navigate('Initial')
                    }}
                />
                <Button
                    title = 'Discard'
                    onpress = {() => navigate('Initial')}
                />
            </View>
        )
    }
}

export default class App extends React.Component {
    static navigationOptions ={
        title: "MasterMind",
    }

    constructor(){
        super();
        //this.state = {}
    }

    render() {
        return (
            <View style={styles.containerMain}>
                <StatusBar hidden/>
                <FlatList
                    data = {memos} //aray of objects in JSON format
                    renderItem = {
                        ({ item }) =>
                            <TouchableWithoutFeedback onPress = {() => navigate('EditItem', { data: this.props.data.id })}>
                                <View style={this.styles.containerElement}>
                                    <Text>'Title:'</Text>
                                    <Text>{item.title}</Text>
                                    <Text>'Memo:'</Text>
                                    <Text>{item.memo}</Text>
                                </View>
                            </TouchableWithoutFeedback>
                    }
                />
            </View>
        );
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
    }
});

export default StackNavigator({
    Initial: { screen: App },
    EditItem: { screen: EditItemScreen },
});