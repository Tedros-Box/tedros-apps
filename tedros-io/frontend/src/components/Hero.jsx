import { motion } from 'framer-motion';
import { useTranslation, Trans } from 'react-i18next';
import Button from './ui/Button';
import { ArrowRight, Bot, Code2, Database } from 'lucide-react';

const Hero = () => {
    const { t } = useTranslation();

    return (
        <section className="relative min-h-screen flex items-center pt-20 overflow-hidden">
            {/* Background Elements */}
            <div className="absolute top-0 right-0 w-[500px] h-[500px] bg-blue-600/20 rounded-full blur-[120px] -translate-y-1/2 translate-x-1/2" />
            <div className="absolute bottom-0 left-0 w-[500px] h-[500px] bg-violet-600/20 rounded-full blur-[120px] translate-y-1/2 -translate-x-1/2" />

            <div className="container relative z-10 grid grid-cols-1 lg:grid-cols-2 gap-12 items-center">
                <motion.div
                    initial={{ opacity: 0, y: 20 }}
                    animate={{ opacity: 1, y: 0 }}
                    transition={{ duration: 0.8 }}
                >
                    <h1 className="text-4xl lg:text-6xl font-bold mb-6 leading-tight">
                        <Trans i18nKey="hero.title">
                            Escale seu Outsourcing de TI com <span className="text-gradient">Sistemas Robustos</span> e <span className="text-gradient">IA Nativa</span>
                        </Trans>
                    </h1>

                    <p className="text-lg text-slate-300 mb-8 max-w-xl">
                        {t('hero.subtitle')}
                    </p>

                    <div className="flex flex-wrap gap-4">
                        <Button variant="primary" onClick={() => document.getElementById('contato').scrollIntoView({ behavior: 'smooth' })}>
                            {t('hero.cta.primary')} <ArrowRight className="ml-2 w-4 h-4" />
                        </Button>
                        <Button variant="outline" onClick={() => document.getElementById('recursos').scrollIntoView({ behavior: 'smooth' })}>
                            {t('hero.cta.secondary')}
                        </Button>
                    </div>

                    <div className="mt-12 flex gap-8 border-t border-gray-800 pt-8">
                        <div>
                            <p className="text-3xl font-bold text-white">12+</p>
                            <p className="text-sm text-slate-500">{t('hero.stats.experience')}</p>
                        </div>
                        <div>
                            <p className="text-3xl font-bold text-white">100%</p>
                            <p className="text-sm text-slate-500">{t('hero.stats.tech')}</p>
                        </div>
                    </div>
                </motion.div>

                <motion.div
                    initial={{ opacity: 0, scale: 0.9 }}
                    animate={{ opacity: 1, scale: 1 }}
                    transition={{ duration: 0.8, delay: 0.2 }}
                    className="relative"
                >
                    <div className="glass-card p-8 relative">
                        <div className="grid grid-cols-2 gap-4">
                            <div className="bg-[#1e293b] p-6 rounded-xl border border-gray-700 hover:border-blue-500/50 transition-colors">
                                <Bot className="w-10 h-10 text-blue-500 mb-4" />
                                <h3 className="text-lg font-semibold mb-2">{t('hero.cards.ai.title')}</h3>
                                <p className="text-sm text-slate-400">{t('hero.cards.ai.desc')}</p>
                            </div>
                            <div className="bg-[#1e293b] p-6 rounded-xl border border-gray-700 hover:border-violet-500/50 transition-colors">
                                <Code2 className="w-10 h-10 text-violet-500 mb-4" />
                                <h3 className="text-lg font-semibold mb-2">{t('hero.cards.lowcode.title')}</h3>
                                <p className="text-sm text-slate-400">{t('hero.cards.lowcode.desc')}</p>
                            </div>
                            <div className="bg-[#1e293b] p-6 rounded-xl border border-gray-700 hover:border-emerald-500/50 transition-colors col-span-2">
                                <Database className="w-10 h-10 text-emerald-500 mb-4" />
                                <h3 className="text-lg font-semibold mb-2">{t('hero.cards.architecture.title')}</h3>
                                <p className="text-sm text-slate-400">{t('hero.cards.architecture.desc')}</p>
                            </div>
                        </div>
                    </div>
                </motion.div>
            </div>
        </section>
    );
};

export default Hero;
