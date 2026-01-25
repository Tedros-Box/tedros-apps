import { useState, useEffect } from 'react';
import { motion, AnimatePresence } from 'framer-motion';
import { useTranslation } from 'react-i18next';
import { Menu, X } from 'lucide-react';
import Button from './ui/Button';

import LanguageSwitcher from './LanguageSwitcher';

const Navbar = () => {
    const { t } = useTranslation();
    const [isScrolled, setIsScrolled] = useState(false);
    const [mobileMenuOpen, setMobileMenuOpen] = useState(false);

    useEffect(() => {
        const handleScroll = () => {
            setIsScrolled(window.scrollY > 20);
        };
        window.addEventListener('scroll', handleScroll);
        return () => window.removeEventListener('scroll', handleScroll);
    }, []);

    const navLinks = [
        { name: t('navbar.links.about'), href: '#sobre' },
        { name: t('navbar.links.features'), href: '#recursos' },
        { name: t('navbar.links.usecases'), href: '#casos-de-uso' },
        { name: t('navbar.links.contact'), href: '#contato' },
    ];

    return (
        <nav className={`fixed top-0 left-0 w-full z-50 transition-all duration-300 ${isScrolled ? 'glass-card border-none rounded-none py-4' : 'bg-transparent py-6'}`}>
            <div className="container flex items-center justify-between">
                {/* Logo Area */}
                <a href="#" className="flex items-center relative">
                    <img
                        src="./images/logo-tedros-small.png"
                        alt="Tedros Logo"
                        className="h-16 w-auto absolute -left-20 top-1/2 -translate-y-1/2 drop-shadow-2xl z-20 hover:scale-110 transition-transform duration-300"
                    />
                    <span className="text-2xl font-bold font-[Outfit] text-white ml-4">
                        Tedros<span className="text-blue-500">.io</span>
                    </span>
                </a>

                {/* Desktop Menu */}
                <div className="hidden md:flex items-center gap-8">
                    {navLinks.map((link) => (
                        <a key={link.name} href={link.href} className="text-sm font-medium text-slate-300 hover:text-white transition-colors">
                            {link.name}
                        </a>
                    ))}
                    <LanguageSwitcher />
                    <Button variant="primary" onClick={() => document.getElementById('contato').scrollIntoView({ behavior: 'smooth' })}>
                        {t('navbar.cta')}
                    </Button>
                </div>

                {/* Mobile Toggle */}
                <button className="md:hidden text-white" onClick={() => setMobileMenuOpen(!mobileMenuOpen)}>
                    {mobileMenuOpen ? <X size={24} /> : <Menu size={24} />}
                </button>
            </div>

            {/* Mobile Menu */}
            <AnimatePresence>
                {mobileMenuOpen && (
                    <motion.div
                        initial={{ opacity: 0, height: 0 }}
                        animate={{ opacity: 1, height: 'auto' }}
                        exit={{ opacity: 0, height: 0 }}
                        className="md:hidden bg-[#0f172a] border-b border-gray-800 absolute w-full"
                    >
                        <div className="flex flex-col p-6 gap-4">
                            {navLinks.map((link) => (
                                <a
                                    key={link.name}
                                    href={link.href}
                                    className="text-white text-lg"
                                    onClick={() => setMobileMenuOpen(false)}
                                >
                                    {link.name}
                                </a>
                            ))}
                            <Button variant="primary" className="w-full" onClick={() => {
                                setMobileMenuOpen(false);
                                document.getElementById('contato').scrollIntoView({ behavior: 'smooth' });
                            }}>
                                {t('navbar.cta')}
                            </Button>
                            <div className="flex justify-center py-2">
                                <LanguageSwitcher />
                            </div>
                        </div>
                    </motion.div>
                )}
            </AnimatePresence>
        </nav>
    );
};

export default Navbar;
