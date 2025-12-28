import Layout from './components/Layout';
import Hero from './components/Hero';
import StrategicAdvantages from './components/StrategicAdvantages';
import DesktopVsWeb from './components/DesktopVsWeb';
import Features from './components/Features';
import Screenshots from './components/Screenshots';
import UseCases from './components/UseCases';
import Tutorial from './components/Tutorial';
import Contact from './components/Contact';

function App() {
    return (
        <Layout>
            <Hero />
            <StrategicAdvantages />
            <DesktopVsWeb />
            <UseCases />
            <Features />
            <Screenshots />
            <Tutorial />
            <Contact />
        </Layout>
    );
}

export default App;
