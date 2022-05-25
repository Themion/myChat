import { Provider } from 'react-redux';
import { 
    BrowserRouter as Router, 
    Route, 
    Routes
} from 'react-router-dom';
import { clientStore } from './app/stompStore';
import Chatroom from './routes/Chatroom';
import Home from './routes/Home';
import Signup from './routes/Signup';

function App() {
    return (
        <Router>
            <div className="App">
                <Routes>
                    <Route path="/signup" element={<Signup />} />
                    <Route path="/:id" element={<Provider store={clientStore}><Chatroom /></Provider>} />
                    <Route path="/" element={<Home />} />
                </Routes>
            </div>
        </Router>
    );
}

export default App;
