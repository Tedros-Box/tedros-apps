import { motion } from 'framer-motion';
import { Monitor, Shield, Layers, Cpu } from 'lucide-react';

const DesktopVsWeb = () => {
    const reasons = [
        {
            icon: <Layers className="w-6 h-6 text-blue-400" />,
            title: "Multi-Tarefa Real",
            desc: "Janelas destacáveis e suporte multi-monitor permitem que operadores gerenciem fluxos de trabalho complexos sem fadiga de abas."
        },
        {
            icon: <Cpu className="w-6 h-6 text-violet-400" />,
            title: "Performance Nativa",
            desc: "Evite o DOM do navegador. Obtenha acesso direto ao hardware para cálculos pesados e resposta de UI instantânea."
        },
        {
            icon: <Shield className="w-6 h-6 text-emerald-400" />,
            title: "Superfície de Ataque Reduzida",
            desc: "Ambiente de execução isolado. Imune a ataques comuns basedos em navegador como XSS e roubo de dados por extensões."
        },
        {
            icon: <Monitor className="w-6 h-6 text-amber-400" />,
            title: "Integração de Periféricos",
            desc: "Comunicação transparente com impressoras, balanças e drivers biométricos sem pontes complexas de navegador."
        }
    ];

    return (
        <section className="section-padding relative overflow-hidden">
            {/* Background Gradient */}
            <div className="absolute inset-0 bg-gradient-to-b from-[#0f172a] to-[#1e293b] -z-10" />

            <div className="container">
                <div className="flex flex-col lg:flex-row gap-16 items-center">
                    <motion.div
                        initial={{ opacity: 0, x: -20 }}
                        whileInView={{ opacity: 1, x: 0 }}
                        viewport={{ once: true }}
                        transition={{ duration: 0.8 }}
                        className="flex-1"
                    >
                        <h2 className="text-3xl md:text-4xl font-bold mb-6">
                            Por que Líderes Globais <br />
                            <span className="text-gradient">Escolhem Desktop</span>
                        </h2>
                        <p className="text-slate-300 text-lg mb-8 leading-relaxed">
                            Grandes players como SAP e TOTVS confiam em arquiteturas Rich Client para operações críticas.
                            Enquanto apps web são ótimos para uso leve, cenários B2B complexos exigem o poder bruto e a estabilidade que apenas um ambiente Desktop nativo pode prover.
                        </p>

                        <div className="p-6 bg-blue-900/10 border border-blue-500/20 rounded-xl">
                            <h4 className="font-bold text-blue-400 mb-2">O Caso de Negócio</h4>
                            <p className="text-sm text-slate-400">
                                Pare de lutar contra limitações do browser. Dê aos seus clientes uma ferramenta de trabalho robusta que respeita sua produtividade e segurança de dados.
                            </p>
                        </div>
                    </motion.div>

                    <div className="flex-1 grid grid-cols-1 md:grid-cols-2 gap-6 w-full">
                        {reasons.map((item, index) => (
                            <motion.div
                                key={index}
                                initial={{ opacity: 0, y: 20 }}
                                whileInView={{ opacity: 1, y: 0 }}
                                viewport={{ once: true }}
                                transition={{ delay: index * 0.1 }}
                                className="glass-card p-6 border-l-4 border-l-blue-500 hover:bg-slate-800/80 transition-all"
                            >
                                <div className="mb-4 bg-slate-800 p-3 rounded-lg w-fit">
                                    {item.icon}
                                </div>
                                <h3 className="text-lg font-bold text-white mb-2">{item.title}</h3>
                                <p className="text-sm text-slate-400">{item.desc}</p>
                            </motion.div>
                        ))}
                    </div>
                </div>
            </div>
        </section>
    );
};

export default DesktopVsWeb;
