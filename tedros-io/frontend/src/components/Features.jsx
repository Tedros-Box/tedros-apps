import { motion } from 'framer-motion';
import { Bot, Mail, Users, FileText, ShoppingCart, Settings } from 'lucide-react';

const Features = () => {
    const apps = [
        { name: "Chatbot IA", icon: <Bot />, desc: "Assistente inteligente para operações, análise de dados e automação de marketing." },
        { name: "Notificações", icon: <Mail />, desc: "Gerenciamento completo de emails, filas de envio e agendamentos." },
        { name: "Usuários", icon: <Users />, desc: "Controle total de perfis, hierarquias e políticas de acesso." },
        { name: "Relatórios", icon: <FileText />, desc: "Geração dinâmica de relatórios e gráficos com a identidade da empresa." },
        { name: "Inventário", icon: <ShoppingCart />, desc: "Gestão de produtos, estoque e movimentações em tempo real." },
        { name: "Configurações", icon: <Settings />, desc: "Ferramentas de temas, internacionalização e preferências do sistema." }
    ];

    return (
        <section id="recursos" className="section-padding relative overflow-hidden">
            <div className="absolute top-1/2 left-1/2 -translate-x-1/2 -translate-y-1/2 w-[800px] h-[800px] bg-blue-600/5 rounded-full blur-[100px]" />

            <div className="container relative z-10">
                <div className="mb-16">
                    <h2 className="text-3xl md:text-4xl font-bold mb-6">Ecossistema <span className="text-gradient">Integrado</span></h2>
                    <div className="flex flex-col lg:flex-row gap-12 items-center">
                        <div className="flex-1">
                            <p className="text-slate-300 text-lg mb-6 leading-relaxed">
                                O Tedros Box não é apenas um sistema, é um framework completo de aplicativos interconectados.
                                O destaque é nossa <strong>Integração Nativa com IA</strong>, permitindo que assistentes analisem dados cruzados (como estoque e clientes) para gerar insights valiosos.
                            </p>
                            <ul className="space-y-4">
                                {[
                                    "Geração e envio de e-mails de marketing automatizados.",
                                    "Análise de consistência em demandas (Jira/Redmine).",
                                    "Revisão de código e operações assistidas."
                                ].map((item, i) => (
                                    <li key={i} className="flex items-center gap-3 text-slate-400">
                                        <span className="w-2 h-2 bg-blue-500 rounded-full" />
                                        {item}
                                    </li>
                                ))}
                            </ul>
                        </div>

                        <div className="flex-1 grid grid-cols-1 sm:grid-cols-2 gap-4 w-full">
                            {apps.map((app, index) => (
                                <motion.div
                                    key={index}
                                    initial={{ opacity: 0, scale: 0.95 }}
                                    whileInView={{ opacity: 1, scale: 1 }}
                                    viewport={{ once: true }}
                                    transition={{ delay: index * 0.05 }}
                                    className="glass-card p-4 flex items-start gap-4 hover:bg-slate-800/50 transition-colors"
                                >
                                    <div className="p-2 bg-gradient-to-br from-blue-500/20 to-violet-500/20 rounded-lg text-blue-400">
                                        {app.icon}
                                    </div>
                                    <div>
                                        <h4 className="font-bold text-white mb-1">{app.name}</h4>
                                        <p className="text-xs text-slate-400">{app.desc}</p>
                                    </div>
                                </motion.div>
                            ))}
                        </div>
                    </div>
                </div>
            </div>
        </section>
    );
};

export default Features;
