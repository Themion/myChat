import { Provider } from 'react-redux';
import { 
    BrowserRouter as Router, 
    Route, 
    Routes
} from 'react-router-dom';
import { store } from './app/store';
import Chatroom from './routes/Chatroom';
import Home from './routes/Home';
import Login from './routes/Login';
import Signup from './routes/Signup';

const App = () => {
    return (
        <Router>
            <div className="App">
                <Routes>
                    <Route path="/signup" element={<Signup />} />
                    <Route path="/login" element={<Login />} />
                    <Route path="/room/:id" element={<Provider store={store}><Chatroom /></Provider>} />
                    <Route path="/" element={<Home />} />
                </Routes>
            </div>
        </Router>
    );
}

export default App;
