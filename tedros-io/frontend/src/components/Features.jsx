import { motion } from 'framer-motion';
import { useTranslation } from 'react-i18next';
import { Bot, Mail, Users, FileText, ShoppingCart, Settings } from 'lucide-react';

const Features = () => {
    const { t, i18n } = useTranslation();

    // DEBUG: Remove after fixing
    console.log('Features Rendering. Lang:', i18n.language, 'Title:', t('features.title'));

    const apps = [
        { name: t('features.apps.chatbot.name'), icon: <Bot />, desc: t('features.apps.chatbot.desc') },
        { name: t('features.apps.notifications.name'), icon: <Mail />, desc: t('features.apps.notifications.desc') },
        { name: t('features.apps.users.name'), icon: <Users />, desc: t('features.apps.users.desc') },
        { name: t('features.apps.reports.name'), icon: <FileText />, desc: t('features.apps.reports.desc') },
        { name: t('features.apps.inventory.name'), icon: <ShoppingCart />, desc: t('features.apps.inventory.desc') },
        { name: t('features.apps.settings.name'), icon: <Settings />, desc: t('features.apps.settings.desc') }
    ];

    return (
        <section id="recursos" className="section-padding relative overflow-hidden">
            <div className="absolute top-1/2 left-1/2 -translate-x-1/2 -translate-y-1/2 w-[800px] h-[800px] bg-blue-600/5 rounded-full blur-[100px]" />

            <div className="container relative z-10">
                <div className="mb-16">
                    <h2 className="text-3xl md:text-4xl font-bold mb-6">{t('features.title')} <span className="text-gradient">{t('features.titleHighlight')}</span></h2>
                    <div className="flex flex-col lg:flex-row gap-12 items-center">
                        <div className="flex-1">
                            <p className="text-slate-300 text-lg mb-6 leading-relaxed" dangerouslySetInnerHTML={{ __html: t('features.description') }} />
                            <ul className="space-y-4">
                                {[
                                    t('features.list.marketing'),
                                    t('features.list.consistency'),
                                    t('features.list.review')
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
