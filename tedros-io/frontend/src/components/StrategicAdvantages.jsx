import { motion } from 'framer-motion';
import { useTranslation, Trans } from 'react-i18next';
import { TrendingUp, Brain, ShieldCheck, Zap } from 'lucide-react';

const StrategicAdvantages = () => {
    const { t } = useTranslation();

    const advantages = [
        {
            icon: <TrendingUp className="w-8 h-8 text-blue-500" />,
            title: t('advantages.list.profit.title'),
            description: t('advantages.list.profit.desc')
        },
        {
            icon: <Brain className="w-8 h-8 text-violet-500" />,
            title: t('advantages.list.ai.title'),
            description: t('advantages.list.ai.desc')
        },
        {
            icon: <ShieldCheck className="w-8 h-8 text-emerald-500" />,
            title: t('advantages.list.stability.title'),
            description: t('advantages.list.stability.desc')
        },
        {
            icon: <Zap className="w-8 h-8 text-amber-500" />,
            title: t('advantages.list.time.title'),
            description: t('advantages.list.time.desc')
        }
    ];

    return (
        <section id="advantages" className="section-padding bg-[#0f172a]">
            <div className="container">
                <div className="text-center mb-16">
                    <h2 className="text-3xl md:text-4xl font-bold mb-4">
                        <Trans i18nKey="advantages.title">
                            Vantagens Estratégicas para <span className="text-gradient">Parceiros de TI</span>
                        </Trans>
                    </h2>
                    <p className="text-slate-400 max-w-2xl mx-auto">
                        {t('advantages.subtitle')}
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
