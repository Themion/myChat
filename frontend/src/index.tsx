import { createRoot } from 'react-dom/client';
import App from './App';

const container = document.getElementById('root')!;
const root = createRoot(container);

// 렌더링 두 번 실행 방지
// root.render(
//     <React.StrictMode>
//         <App />
//     </React.StrictMode>
// );

root.render(<App />);
