import Layout from './components/Layout';
import Hero from './components/Hero';
import About from './components/About';
import Features from './components/Features';
import Screenshots from './components/Screenshots';
import UseCases from './components/UseCases';
import Contact from './components/Contact';

function App() {
    return (
        <Layout>
            <Hero />
            <About />
            <Features />
            <Screenshots />
            <UseCases />
            <Contact />
        </Layout>
    );
}

export default App;
