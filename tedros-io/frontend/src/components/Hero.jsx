import { motion } from 'framer-motion';
import Button from './ui/Button';
import { ArrowRight, Bot, Code2, Database } from 'lucide-react';

const Hero = () => {
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
                        Escale seu Outsourcing de TI com <span className="text-gradient">Sistemas Robustos</span> e <span className="text-gradient">IA Nativa</span>
                    </h1>

                    <p className="text-lg text-slate-300 mb-8 max-w-xl">
                        Reduza custos de desenvolvimento em até 60% e entregue aplicações Desktop de nível corporativo—nível SAP/TOTVS—com equipes enxutas e time-to-market acelerado.
                    </p>

                    <div className="flex flex-wrap gap-4">
                        <Button variant="primary" onClick={() => document.getElementById('contato').scrollIntoView({ behavior: 'smooth' })}>
                            Agende uma Demo para Parceiros <ArrowRight className="ml-2 w-4 h-4" />
                        </Button>
                        <Button variant="outline" onClick={() => document.getElementById('recursos').scrollIntoView({ behavior: 'smooth' })}>
                            Conheça o Ecossistema
                        </Button>
                    </div>

                    <div className="mt-12 flex gap-8 border-t border-gray-800 pt-8">
                        <div>
                            <p className="text-3xl font-bold text-white">12+</p>
                            <p className="text-sm text-slate-500">Anos de Experiência</p>
                        </div>
                        <div>
                            <p className="text-3xl font-bold text-white">100%</p>
                            <p className="text-sm text-slate-500">Java & Open Source</p>
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
                                <h3 className="text-lg font-semibold mb-2">IA Integrada</h3>
                                <p className="text-sm text-slate-400">Chatbot para operações e análise de dados.</p>
                            </div>
                            <div className="bg-[#1e293b] p-6 rounded-xl border border-gray-700 hover:border-violet-500/50 transition-colors">
                                <Code2 className="w-10 h-10 text-violet-500 mb-4" />
                                <h3 className="text-lg font-semibold mb-2">Low Code</h3>
                                <p className="text-sm text-slate-400">Geração dinâmica de telas e formulários.</p>
                            </div>
                            <div className="bg-[#1e293b] p-6 rounded-xl border border-gray-700 hover:border-emerald-500/50 transition-colors col-span-2">
                                <Database className="w-10 h-10 text-emerald-500 mb-4" />
                                <h3 className="text-lg font-semibold mb-2">Cliente-Servidor Robusto</h3>
                                <p className="text-sm text-slate-400">Arquitetura JavaEE com OpenEJB e tolerância a falhas.</p>
                            </div>
                        </div>
                    </div>
                </motion.div>
            </div>
        </section>
    );
};

export default Hero;
