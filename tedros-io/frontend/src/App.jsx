import React, { useState, useEffect } from 'react';
import { Menu, X, Terminal, Monitor, CheckCircle, Database, Shield, Zap, ChevronRight, MessageSquare, ArrowRight, ArrowLeft } from 'lucide-react';
import { motion, AnimatePresence } from 'framer-motion';

// Mock list of screenshots - normally we might fetch this list, but hardcoding for the demo is fine
// These match the files we moved to public/images/Screenshots
const SCREENSHOTS = [
    "153532.png", "153641.png", "154555.png", "154929.png",
    "155147.png", "155550.png", "160036.png", "160609.png"
].map(name => `./images/Screenshots/${name}`);

function App() {
    const [isMenuOpen, setIsMenuOpen] = useState(false);
    const [scrolled, setScrolled] = useState(false);

    useEffect(() => {
        const handleScroll = () => setScrolled(window.scrollY > 50);
        window.addEventListener('scroll', handleScroll);
        return () => window.removeEventListener('scroll', handleScroll);
    }, []);

    const scrollToSection = (id) => {
        const element = document.getElementById(id);
        if (element) {
            element.scrollIntoView({ behavior: 'smooth' });
        }
        setIsMenuOpen(false);
    };

    return (
        <div className="app-wrapper">
            {/* Navigation */}
            <nav className={`fixed-nav ${scrolled ? 'scrolled' : ''}`} style={{
                position: 'fixed', width: '100%', zIndex: 1000,
                backgroundColor: scrolled ? 'var(--color-bg-alt)' : 'transparent',
                borderBottom: scrolled ? '1px solid var(--glass-border)' : 'none',
                transition: 'all 0.3s ease'
            }}>
                <div className="container" style={{ height: 'var(--header-height)', display: 'flex', alignItems: 'center', justifyContent: 'space-between' }}>
                    <div className="logo" style={{ fontSize: '1.5rem', fontWeight: 'bold' }}>
                        <span className="text-gradient">TEDROS.IO</span>
                    </div>

                    {/* Desktop Menu */}
                    <div className="desktop-menu" style={{ display: 'flex', gap: '2rem', alignItems: 'center' }}>
                        {['Problema', 'Solução', 'Arquitetura', 'Showcase'].map((item) => (
                            <button key={item} onClick={() => scrollToSection(item.toLowerCase())} style={{ color: 'var(--color-text-muted)', fontSize: '0.9rem' }}>
                                {item}
                            </button>
                        ))}
                        <button onClick={() => scrollToSection('contact')} className="btn btn-primary" style={{ padding: '0.5rem 1.25rem' }}>
                            Agendar Demo
                        </button>
                    </div>

                    {/* Mobile Toggle */}
                    <div className="mobile-toggle" style={{ display: 'none' }}>
                        <button onClick={() => setIsMenuOpen(!isMenuOpen)}>
                            {isMenuOpen ? <X color="white" /> : <Menu color="white" />}
                        </button>
                    </div>
                </div>
            </nav>

            <main>
                <HeroSection onCtaClick={() => scrollToSection('contact')} />
                <ProblemSection id="problema" />
                <SolutionSection id="solução" />
                <ScreenshotCarousel id="showcase" images={SCREENSHOTS} />
                <ArchitectureSection id="arquitetura" />
                <ContactSection id="contact" />
            </main>

            <Footer />

            <style>{`
                @media (max-width: 768px) {
                    .desktop-menu { display: none !important; }
                    .mobile-toggle { display: block !important; }
                }
            `}</style>
        </div>
    );
}

const HeroSection = ({ onCtaClick }) => (
    <section style={{
        position: 'relative', minHeight: '100vh', display: 'flex',
        alignItems: 'center', overflow: 'hidden', paddingTop: '80px'
    }}>
        {/* Abstract Background Elements */}
        <div className="glow-bg" style={{ top: '-20%', left: '-10%' }} />
        <div className="glow-bg" style={{ bottom: '-10%', right: '-10%', background: 'radial-gradient(circle, var(--color-accent-glow) 0%, transparent 70%)' }} />

        <div className="container" style={{ position: 'relative', zIndex: 10, textAlign: 'center' }}>
            <motion.div
                initial={{ opacity: 0, y: 30 }}
                animate={{ opacity: 1, y: 0 }}
                transition={{ duration: 0.8 }}
            >
                <span style={{
                    display: 'inline-block', padding: '0.5rem 1rem', borderRadius: '50px',
                    background: 'rgba(59, 130, 246, 0.1)', border: '1px solid rgba(59, 130, 246, 0.2)',
                    color: '#60a5fa', fontSize: '0.875rem', fontWeight: '600', marginBottom: '1.5rem'
                }}>
                    Enterprise AI for Software Delivery
                </span>

                <h1 style={{ marginBottom: '2rem' }}>
                    Sua Fábrica de Software <br />
                    <span className="text-gradient">Gerenciada por IA</span>
                </h1>

                <p style={{
                    fontSize: '1.25rem', color: 'var(--color-text-muted)',
                    maxWidth: '800px', margin: '0 auto 3rem auto'
                }}>
                    O Tedros elimina gargalos no <strong>Redmine</strong> e <strong>GitLab</strong>, automatiza code reviews e organiza evidências. Recupere o controle sobre seus 200+ sistemas.
                </p>

                <div style={{ display: 'flex', gap: '1rem', justifyContent: 'center', flexWrap: 'wrap' }}>
                    <button onClick={onCtaClick} className="btn btn-primary" style={{ fontSize: '1.1rem', padding: '1rem 2rem' }}>
                        Solicitar Demonstração <ChevronRight size={20} />
                    </button>
                    <button className="btn btn-secondary" style={{ fontSize: '1.1rem', padding: '1rem 2rem' }}>
                        <Monitor size={20} /> Ver em Ação
                    </button>
                </div>
            </motion.div>
        </div>
    </section>
);

const ProblemSection = ({ id }) => (
    <section id={id} className="section-padding" style={{ backgroundColor: 'var(--color-bg-alt)' }}>
        <div className="container">
            <div style={{ textAlign: 'center', marginBottom: '4rem' }}>
                <h2>O Caos na Gestão de Demandas</h2>
                <p style={{ color: 'var(--color-text-muted)', fontSize: '1.1rem', marginTop: '1rem' }}>
                    Cenários complexos com JBoss, WSO2 e múltiplas equipes geram gargalos invisíveis.
                </p>
            </div>

            <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(300px, 1fr))', gap: '2rem' }}>
                <ProblemCard
                    icon={<MessageSquare color="#f87171" />}
                    title="Backlog do Redmine"
                    desc="Volume incontrolável de issues abertas e dificuldade em priorizar o que realmente importa."
                />
                <ProblemCard
                    icon={<Terminal color="#fb923c" />}
                    title="Code Review Lento"
                    desc="Merge Requests parados por dias aguardando revisão humana, atrasando o deploy."
                />
                <ProblemCard
                    icon={<Database color="#facc15" />}
                    title="Evidências Dispersas"
                    desc="Dificuldade em auditar o trabalho realizado e aprovar pagamentos ou entregas."
                />
            </div>
        </div>
    </section>
);

const ProblemCard = ({ icon, title, desc }) => (
    <div className="card">
        <div style={{
            background: 'rgba(255,255,255,0.05)', padding: '0.75rem',
            borderRadius: '8px', width: 'fit-content', marginBottom: '1.5rem'
        }}>
            {icon}
        </div>
        <h3 style={{ marginBottom: '1rem' }}>{title}</h3>
        <p style={{ color: 'var(--color-text-muted)' }}>{desc}</p>
    </div>
);

const SolutionSection = ({ id }) => (
    <section id={id} className="section-padding">
        <div className="container" style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(350px, 1fr))', gap: '4rem', alignItems: 'center' }}>
            <div>
                <h2>
                    Tedros: Inteligência que <br />
                    <span style={{ color: 'var(--color-accent)' }}>Executa</span>.
                </h2>
                <p style={{ color: 'var(--color-text-muted)', fontSize: '1.1rem', margin: '1.5rem 0' }}>
                    Diferente de chatbots passivos, o Tedros possui <strong>tools executáveis</strong> integradas ao seu ambiente.
                </p>

                <ul style={{ listStyle: 'none', display: 'flex', flexDirection: 'column', gap: '1rem' }}>
                    {[
                        "Resume e prioriza issues do Redmine.",
                        "Code Review autônomo em MRs do GitLab.",
                        "Captura e organiza screenshots de evidência.",
                        "Relatórios de saúde dos 200+ sistemas."
                    ].map((item, i) => (
                        <li key={i} style={{ display: 'flex', alignItems: 'start', gap: '0.75rem' }}>
                            <CheckCircle color="var(--color-accent)" size={20} style={{ flexShrink: 0, marginTop: '2px' }} />
                            <span>{item}</span>
                        </li>
                    ))}
                </ul>
            </div>

            {/* Visual Representation of Agent Logic */}
            <div className="card" style={{ padding: '0', overflow: 'hidden', border: '1px solid var(--color-primary-glow)' }}>
                <div style={{ background: '#1e293b', padding: '1rem', borderBottom: '1px solid #334155', display: 'flex', gap: '0.5rem' }}>
                    <div style={{ width: 10, height: 10, borderRadius: '50%', background: '#EF4444' }} />
                    <div style={{ width: 10, height: 10, borderRadius: '50%', background: '#F59E0B' }} />
                    <div style={{ width: 10, height: 10, borderRadius: '50%', background: '#10B981' }} />
                    <span style={{ marginLeft: '1rem', fontFamily: 'monospace', fontSize: '0.8rem', color: '#94a3b8' }}>tedros-agent --active</span>
                </div>
                <div style={{ padding: '1.5rem', fontFamily: 'monospace', fontSize: '0.9rem' }}>
                    <div style={{ marginBottom: '1rem', color: '#94a3b8' }}>$ checking gitlab_merge_requests...</div>
                    <div style={{ marginBottom: '1rem', color: '#3b82f6' }}>{'>'} Found 3 critical MRs. Analyzing diffs...</div>
                    <div style={{ background: 'rgba(16, 185, 129, 0.1)', padding: '1rem', borderRadius: '8px', color: '#6ee7b7' }}>
                        [SUCCESS] MR !452 validated.<br />
                        No security leaks found.<br />
                        Performance impact: Low.
                    </div>
                </div>
            </div>
        </div>
    </section>
);

const ScreenshotCarousel = ({ id, images }) => {
    const [index, setIndex] = useState(0);

    const next = () => setIndex((prev) => (prev + 1) % images.length);
    const prev = () => setIndex((prev) => (prev - 1 + images.length) % images.length);

    useEffect(() => { // Auto-advance
        const timer = setInterval(next, 5000);
        return () => clearInterval(timer);
    }, []);

    return (
        <section id={id} className="section-padding" style={{ backgroundColor: '#000', position: 'relative' }}>
            <div className="container" style={{ textAlign: 'center' }}>
                <h2 style={{ marginBottom: '3rem' }}>Interface do Operador</h2>

                <div style={{ position: 'relative', maxWidth: '1000px', margin: '0 auto' }}>
                    {/* Image Frame */}
                    <div style={{
                        position: 'relative', aspectRatio: '16/9', overflow: 'hidden',
                        borderRadius: '12px', border: '1px solid #334155',
                        boxShadow: '0 20px 50px -12px rgba(0, 0, 0, 0.5)'
                    }}>
                        <AnimatePresence mode="wait">
                            <motion.img
                                key={index}
                                src={images[index]}
                                initial={{ opacity: 0, x: 100 }}
                                animate={{ opacity: 1, x: 0 }}
                                exit={{ opacity: 0, x: -100 }}
                                transition={{ duration: 0.3 }}
                                style={{ width: '100%', height: '100%', objectFit: 'contain', background: '#1e293b' }}
                                alt="Tedros Interface"
                            />
                        </AnimatePresence>

                        {/* Overlay Controls */}
                        <button onClick={prev} style={{ position: 'absolute', left: '10px', top: '50%', transform: 'translateY(-50%)', background: 'rgba(0,0,0,0.5)', color: 'white', padding: '10px', borderRadius: '50%' }}>
                            <ArrowLeft />
                        </button>
                        <button onClick={next} style={{ position: 'absolute', right: '10px', top: '50%', transform: 'translateY(-50%)', background: 'rgba(0,0,0,0.5)', color: 'white', padding: '10px', borderRadius: '50%' }}>
                            <ArrowRight />
                        </button>
                    </div>

                    <div style={{ marginTop: '1.5rem', fontFamily: 'monospace', color: '#64748b' }}>
                        Screenshot: {images[index].split('/').pop()}
                    </div>
                </div>
            </div>
        </section>
    );
};

const ArchitectureSection = ({ id }) => (
    <section id={id} className="section-padding">
        <div className="container" style={{ textAlign: 'center' }}>
            <h2 style={{ marginBottom: '4rem' }}>Arquitetura Híbrida</h2>
            <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(200px, 1fr))', gap: '2rem' }}>
                <div className="card">
                    <h4 style={{ color: '#60a5fa', marginBottom: '0.5rem' }}>Client Layer</h4>
                    <p style={{ fontSize: '0.9rem', color: '#94a3b8' }}>Web Application & Desktop Tracker</p>
                </div>
                <div className="card" style={{ borderColor: 'var(--color-primary-dark)' }}>
                    <h4 style={{ color: '#fff', marginBottom: '0.5rem' }}>Tedros Core</h4>
                    <p style={{ fontSize: '0.9rem', color: '#94a3b8' }}>Java 17 • Quarkus/TomEE • PostgreSQL</p>
                </div>
                <div className="card">
                    <h4 style={{ color: '#34d399', marginBottom: '0.5rem' }}>AI Module</h4>
                    <p style={{ fontSize: '0.9rem', color: '#94a3b8' }}>LLM Context Window • Vector DB</p>
                </div>
            </div>
        </div>
    </section>
);

const ContactSection = ({ id }) => (
    <section id={id} className="section-padding" style={{ background: 'linear-gradient(to top, #020617, #0f172a)' }}>
        <div className="container" style={{ maxWidth: '600px', textAlign: 'center' }}>
            <h2>Vamos Conversar?</h2>
            <p style={{ color: 'var(--color-text-muted)', marginBottom: '3rem' }}>
                Agende uma apresentação executiva para sua empresa.
            </p>

            <form className="card" style={{ textAlign: 'left', display: 'flex', flexDirection: 'column', gap: '1.5rem' }} onSubmit={(e) => e.preventDefault()}>
                <div>
                    <label style={{ display: 'block', marginBottom: '0.5rem', fontSize: '0.9rem', fontWeight: '500' }}>Email Corporativo</label>
                    <input type="email" placeholder="seu@empresa.com" style={{
                        width: '100%', padding: '0.75rem', borderRadius: '8px',
                        border: '1px solid var(--glass-border)', background: 'rgba(0,0,0,0.2)', color: 'white'
                    }} />
                </div>
                <div>
                    <label style={{ display: 'block', marginBottom: '0.5rem', fontSize: '0.9rem', fontWeight: '500' }}>Mensagem</label>
                    <textarea rows="4" placeholder="Como o Tedros pode ajudar?" style={{
                        width: '100%', padding: '0.75rem', borderRadius: '8px',
                        border: '1px solid var(--glass-border)', background: 'rgba(0,0,0,0.2)', color: 'white'
                    }}></textarea>
                </div>
                <button type="submit" className="btn btn-primary" style={{ justifyContent: 'center', width: '100%' }}>
                    Enviar Solicitação
                </button>
            </form>
        </div>
    </section>
);

const Footer = () => (
    <footer style={{ padding: '2rem 0', borderTop: '1px solid #1e293b', textAlign: 'center', color: '#64748b', fontSize: '0.9rem' }}>
        <div className="container">
            &copy; {new Date().getFullYear()} Tedros.io. Todos os direitos reservados.
        </div>
    </footer>
);

export default App;
