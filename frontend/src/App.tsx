import { 
    BrowserRouter as Router, 
    Route, 
    Routes
} from 'react-router-dom';
import Chatroom from './routes/Chatroom';
import Home from './routes/Home';

function App() {
    return (
        <Router>
            <div className="App">
                <Routes>
                    <Route path="/" element={<Home />} />
                    <Route path="/:id" element={<Chatroom />} />
                </Routes>
            </div>
        </Router>
    );
}

export default App;
