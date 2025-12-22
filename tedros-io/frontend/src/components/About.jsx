import { motion } from 'framer-motion';
import { Layers, Server, Shield, Globe } from 'lucide-react';

const About = () => {
    const features = [
        {
            icon: <Layers className="w-8 h-8 text-blue-500" />,
            title: "Arquitetura Modular",
            description: "Detecta automaticamente apps instalados e gerencia permissões. Contextos isolados para máxima tolerância a falhas."
        },
        {
            icon: <Server className="w-8 h-8 text-violet-500" />,
            title: "Backend Robusto",
            description: "Construído sobre Jakarta EE e OpenEJB, garantindo persistência eficiente, serviços transacionais e processos em background."
        },
        {
            icon: <Shield className="w-8 h-8 text-emerald-500" />,
            title: "Segurança Total",
            description: "Gerenciamento autônomo de usuários, políticas de acesso granulares e auditoria completa de ações."
        },
        {
            icon: <Globe className="w-8 h-8 text-indigo-500" />,
            title: "Internacionalização",
            description: "Suporte nativo a Inglês e Português, com arquitetura pronta para expansão global."
        }
    ];

    return (
        <section id="sobre" className="section-padding bg-[#0f172a]">
            <div className="container">
                <div className="text-center mb-16">
                    <h2 className="text-3xl md:text-4xl font-bold mb-4">Arquitetura de <span className="text-gradient">Alta Performance</span></h2>
                    <p className="text-slate-400 max-w-2xl mx-auto">
                        O Tedros Box foi desenhado para escalabilidade e manutenção simplificada, permitindo que você foque no core business.
                    </p>
                </div>

                <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-8">
                    {features.map((feature, index) => (
                        <motion.div
                            key={index}
                            initial={{ opacity: 0, y: 20 }}
                            whileInView={{ opacity: 1, y: 0 }}
                            viewport={{ once: true }}
                            transition={{ delay: index * 0.1 }}
                            className="glass-card p-6 h-full hover:border-blue-500/30 transition-colors"
                        >
                            <div className="mb-4 bg-slate-800/50 w-16 h-16 rounded-full flex items-center justify-center">
                                {feature.icon}
                            </div>
                            <h3 className="text-xl font-bold mb-3">{feature.title}</h3>
                            <p className="text-slate-400 text-sm leading-relaxed">{feature.description}</p>
                        </motion.div>
                    ))}
                </div>
            </div>
        </section>
    );
};

export default About;
