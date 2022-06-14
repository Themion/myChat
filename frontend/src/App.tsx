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
import Logout from './routes/Logout';
import Signup from './routes/Signup';

const App = () => {
    return (
        <Router>
            <div className="App m-3">
                <Routes>
                    <Route path="/signup" element={<Signup />} />
                    <Route path="/login" element={<Login />} />
                    <Route path="/logout" element={<Logout />} />
                    <Route path="/room/:id" element={<Provider store={store}><Chatroom /></Provider>} />
                    <Route path="/" element={<Home />} />
                </Routes>
            </div>
        </Router>
    );
}

export default App;
