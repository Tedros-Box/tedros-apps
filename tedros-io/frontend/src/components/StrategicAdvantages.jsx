import { motion } from 'framer-motion';
import { TrendingUp, Brain, ShieldCheck, Zap } from 'lucide-react';

const StrategicAdvantages = () => {
    const advantages = [
        {
            icon: <TrendingUp className="w-8 h-8 text-blue-500" />,
            title: "Lucratividade Maximizada",
            description: "Nosso framework abstrai camadas complexas (Segurança, UI, BD), permitindo que devs Júnior/Pleno entreguem resultados de Sênior. Reduza sua folha de pagamento, aumente a margem dos contratos."
        },
        {
            icon: <Brain className="w-8 h-8 text-violet-500" />,
            title: "A Vantagem da IA",
            description: "Entregue sistemas que nascem inteligentes. Integração nativa com IA para auditoria automatizada, revisão de código e automação de processos de negócios."
        },
        {
            icon: <ShieldCheck className="w-8 h-8 text-emerald-500" />,
            title: "Estabilidade Corporativa",
            description: "Vá além das limitações do navegador. Ofereça multi-janelas real, performance nativa de hardware e segurança máxima para dados sensíveis (Gov/LGPD)."
        },
        {
            icon: <Zap className="w-8 h-8 text-amber-500" />,
            title: "Time-to-Market Acelerado",
            description: "Vá do zero ao MVP funcional de ERP/CRM em semanas usando nossa biblioteca de componentes pré-construída e testada em batalha."
        }
    ];

    return (
        <section id="advantages" className="section-padding bg-[#0f172a]">
            <div className="container">
                <div className="text-center mb-16">
                    <h2 className="text-3xl md:text-4xl font-bold mb-4">Vantagens Estratégicas para <span className="text-gradient">Parceiros de TI</span></h2>
                    <p className="text-slate-400 max-w-2xl mx-auto">
                        Posicione sua empresa como líder na transformação digital com uma plataforma construída para lucro e performance.
                    </p>
                </div>

                <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-8">
                    {advantages.map((advantage, index) => (
                        <motion.div
                            key={index}
                            initial={{ opacity: 0, y: 20 }}
                            whileInView={{ opacity: 1, y: 0 }}
                            viewport={{ once: true }}
                            transition={{ delay: index * 0.1 }}
                            className="glass-card p-6 h-full hover:border-blue-500/30 transition-colors"
                        >
                            <div className="mb-4 bg-slate-800/50 w-16 h-16 rounded-full flex items-center justify-center">
                                {advantage.icon}
                            </div>
                            <h3 className="text-xl font-bold mb-3">{advantage.title}</h3>
                            <p className="text-slate-400 text-sm leading-relaxed">{advantage.description}</p>
                        </motion.div>
                    ))}
                </div>
            </div>
        </section>
    );
};

export default StrategicAdvantages;
