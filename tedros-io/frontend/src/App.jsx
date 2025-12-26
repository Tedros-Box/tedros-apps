import Layout from './components/Layout';
import Hero from './components/Hero';
import About from './components/About';
import Features from './components/Features';
import Screenshots from './components/Screenshots';
import UseCases from './components/UseCases';
import Tutorial from './components/Tutorial';
import Contact from './components/Contact';

function App() {
    return (
        <Layout>
            <Hero />
            <About />
            <Features />
            <Screenshots />
            <UseCases />
            <Tutorial />
            <Contact />
        </Layout>
    );
}

export default App;
