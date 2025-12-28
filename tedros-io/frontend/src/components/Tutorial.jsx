
import React, { useState } from 'react';
import { motion, AnimatePresence } from 'framer-motion';
import { Code, FileCode, Layers, Play, X, ZoomIn } from 'lucide-react';
import { PRODUCT_CODE, PRODUCT_MV_CODE, PRODUCT_MODULE_CODE, APP_START_CODE } from './code-examples/tutorial-code';

const Tutorial = () => {
    const [isModalOpen, setIsModalOpen] = useState(false);

    return (
        <section id="tutorial" className="section-padding bg-slate-900 relative overflow-hidden">
            {/* Background decoration */}
            <div className="absolute top-0 left-0 w-full h-full overflow-hidden pointer-events-none opacity-20">
                <div className="absolute -top-[20%] -right-[10%] w-[500px] h-[500px] bg-blue-600/30 rounded-full blur-3xl"></div>
                <div className="absolute top-[40%] -left-[10%] w-[400px] h-[400px] bg-violet-600/30 rounded-full blur-3xl"></div>
            </div>

            <div className="container relative z-10 flex flex-col items-center">
                <motion.div
                    initial={{ opacity: 0, y: 20 }}
                    whileInView={{ opacity: 1, y: 0 }}
                    viewport={{ once: true }}
                    transition={{ duration: 0.6 }}
                    className="text-center mb-16"
                >
                    <h2 className="text-3xl md:text-4xl font-bold mb-6">
                        Como Criar uma Tela CRUD Simples no <span className="text-gradient">Tedros Box</span>
                    </h2>
                    <p className="text-lg text-slate-300 max-w-3xl mx-auto">
                        Veja como é simples adicionar uma nova tela CRUD para a entidade 'Product' no sistema Tedros Box.
                        A seguir, os passos em ordem de precedência, com trechos de código das classes envolvidas.
                    </p>
                </motion.div>

                {/* Project Structure - Moved to Top */}
                <motion.div
                    initial={{ opacity: 0, scale: 0.95 }}
                    whileInView={{ opacity: 1, scale: 1 }}
                    viewport={{ once: true }}
                    transition={{ duration: 0.6 }}
                    className="glass-card p-6 mb-16 w-full max-w-3xl"
                >
                    <h3 className="text-xl font-bold text-white mb-4 flex items-center gap-2">
                        <span className="text-blue-400">#</span> Estrutura do Projeto
                    </h3>
                    <div className="bg-slate-950 rounded-lg p-4 font-mono text-sm text-slate-300 overflow-x-auto border border-slate-700">
                        <pre>{`/app-stock
|-- app-stock-ejb         (Regras de Negócio)
|-- app-stock-ejb-client  (Interfaces Remotas)
|-- app-stock-ejb-ear     (Empacotamento)
|-- app-stock-fx          (Frontend JavaFX)
|-- app-stock-model       (Entidades JPA)`}
                        </pre>
                    </div>
                </motion.div>

                <div className="w-full max-w-4xl space-y-8 mb-16">
                    <StepItem
                        number="1"
                        title="Entidade (Entity)"
                        description="Defina a entidade JPA que representa o objeto de negócio."
                        file="org.tedros.stock.entity.Product"
                        project="app-stock-model"
                        icon={<Code size={20} />}
                        code={PRODUCT_CODE}
                    />

                    <StepItem
                        number="2"
                        title="Model View (MV)"
                        description="Crie a classe ModelView que define como a entidade será apresentada."
                        file="org.tedros.stock.module.products.model.ProductMV"
                        project="app-stock-fx"
                        icon={<Layers size={20} />}
                        code={PRODUCT_MV_CODE}
                    />

                    <StepItem
                        number="3"
                        title="Módulo (Module)"
                        description="Registre a ModelView e configure as permissões no módulo."
                        file="org.tedros.stock.module.products.ProductModule"
                        project="app-stock-fx"
                        icon={<FileCode size={20} />}
                        code={PRODUCT_MODULE_CODE}
                    />

                    <StepItem
                        number="4"
                        title="Inicialização (AppStart)"
                        description="Integre o módulo na inicialização da aplicação."
                        file="org.tedros.stock.start.AppStart"
                        project="app-stock-fx"
                        icon={<Play size={20} />}
                        code={APP_START_CODE}
                    />
                </div>

                {/* Final Result Screenshot - Moved to Bottom */}
                <motion.div
                    initial={{ opacity: 0, y: 30 }}
                    whileInView={{ opacity: 1, y: 0 }}
                    viewport={{ once: true }}
                    transition={{ duration: 0.8 }}
                    className="w-full max-w-5xl glass-card p-2"
                >
                    {/* Desktop: Overlay | Mobile: Stacked */}
                    <div className="relative rounded-xl overflow-hidden shadow-2xl border border-slate-700 bg-slate-900 group">

                        {/* Image Container with click to open modal */}
                        <div
                            className="relative cursor-zoom-in overflow-hidden"
                            onClick={() => setIsModalOpen(true)}
                        >
                            {/* Trying correct relative path for vite base: ./ */}
                            <img
                                src="images/Screenshots/115216.png"
                                alt="Tela de Produtos Gerada no Tedros Box"
                                className="w-full h-auto object-cover transition-transform duration-500 group-hover:scale-105"
                            />
                            {/* Hover overlay hint */}
                            <div className="absolute inset-0 bg-black/0 group-hover:bg-black/10 transition-colors flex items-center justify-center opacity-0 group-hover:opacity-100">
                                <div className="bg-black/50 p-3 rounded-full text-white backdrop-blur-sm">
                                    <ZoomIn size={24} />
                                </div>
                            </div>
                        </div>

                        {/* Content Box - Relative on Mobile, Absolute Overlay on Desktop */}
                        <div className="relative md:absolute bottom-0 left-0 w-full bg-slate-900 md:bg-gradient-to-t md:from-slate-900 md:via-slate-900/90 md:to-transparent p-6 md:p-8 md:pt-24 text-center border-t border-slate-800 md:border-t-0">
                            <span className="inline-block px-4 py-1.5 bg-blue-500 text-white text-sm font-bold rounded-full mb-3 shadow-lg shadow-blue-500/20">
                                Resultado Final
                            </span>
                            <p className="text-white text-xl font-bold">Tela de Produtos Gerada Automaticamente</p>
                            <p className="text-slate-400 text-sm mt-2 md:hidden">Toque na imagem para ampliar</p>
                        </div>
                    </div>
                </motion.div>
            </div>

            {/* Image Modal */}
            <AnimatePresence>
                {isModalOpen && (
                    <motion.div
                        initial={{ opacity: 0 }}
                        animate={{ opacity: 1 }}
                        exit={{ opacity: 0 }}
                        onClick={() => setIsModalOpen(false)}
                        className="fixed inset-0 z-50 flex items-center justify-center bg-black/90 backdrop-blur-sm p-4 cursor-zoom-out"
                    >
                        <motion.div
                            initial={{ scale: 0.9, opacity: 0 }}
                            animate={{ scale: 1, opacity: 1 }}
                            exit={{ scale: 0.9, opacity: 0 }}
                            className="relative max-w-7xl w-full max-h-[90vh] flex items-center justify-center"
                            onClick={(e) => e.stopPropagation()} // Prevent closing when clicking image wrapper (optional, but better UX to close anywhere usually)
                        >
                            <button
                                onClick={() => setIsModalOpen(false)}
                                className="absolute -top-12 right-0 text-white hover:text-blue-400 transition-colors bg-white/10 p-2 rounded-full backdrop-blur-md"
                            >
                                <X size={24} />
                            </button>
                            <img
                                src="images/Screenshots/115216.png"
                                alt="Tela de Produtos Gerada no Tedros Box"
                                className="max-w-full max-h-[85vh] object-contain rounded-lg shadow-2xl border border-white/10"
                            />
                        </motion.div>
                    </motion.div>
                )}
            </AnimatePresence>

        </section>
    );
};

const CodeBlock = ({ code }) => {
    const highlight = (code) => {
        if (!code) return null;

        // Escape function
        const escape = (str) => str.replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;');

        // Tokenize by splitting: Strings OR Comments
        const parts = code.split(/(".*?"|\/\/.*$)/gm);

        // Placeholder for class attribute to prevent collision with 'class' keyword during regex replacement
        const placeholder = '___CLS___';

        const processed = parts.map((part) => {
            // If it's a string, color it green
            if (part.startsWith('"')) {
                return `<span class="text-emerald-400">${escape(part)}</span>`;
            }
            // If it's a comment, color it gray
            if (part.startsWith('//')) {
                return `<span class="text-slate-500">${escape(part)}</span>`;
            }

            // Otherwise, it's code. Apply keywords/types highlighting.
            let chunk = escape(part);

            // Annotations (Yellow)
            chunk = chunk.replace(/(@\w+)/g, `<span ${placeholder}="text-yellow-400">$1</span>`);

            // Keywords (Violet)
            chunk = chunk.replace(/\b(public|class|private|protected|void|return|new|extends|implements|interface|static|final|package|import)\b/g, `<span ${placeholder}="text-violet-400 font-semibold">$1</span>`);

            // Types (Cyan)
            chunk = chunk.replace(/\b([A-Z]\w+)\b/g, `<span ${placeholder}="text-cyan-400">$1</span>`);

            return chunk;
        }).join('');

        // Final swap of placeholder to actual class attribute
        return processed.replace(/___CLS___/g, 'class');
    };

    return (
        <pre className="font-mono text-sm leading-relaxed" dangerouslySetInnerHTML={{ __html: highlight(code) }}></pre>
    );
};


const StepItem = ({ number, title, description, file, project, code, icon }) => (
    <motion.div
        initial={{ opacity: 0, y: 20 }}
        whileInView={{ opacity: 1, y: 0 }}
        viewport={{ once: true }}
        className="relative pl-8 md:pl-0"
    >
        {/* Layout: Number top left, Content box */}
        <div className="glass-card p-6 border-l-4 border-l-blue-500 relative overflow-hidden group">

            {/* Number Watermark */}
            <div className="absolute -right-4 -top-6 text-[120px] font-bold text-slate-800/30 select-none -z-10 group-hover:text-slate-800/50 transition-colors">
                {number}
            </div>

            <div className="flex flex-col md:flex-row md:items-start justify-between mb-6 gap-4">
                <div>
                    <div className="flex items-center gap-3 mb-2">
                        <span className="flex items-center justify-center w-8 h-8 rounded-full bg-blue-500/20 text-blue-400 font-bold border border-blue-500/30">
                            {number}
                        </span>
                        <h3 className="text-xl font-bold text-white">
                            {title}
                        </h3>
                    </div>
                    <p className="text-slate-400 text-sm pl-11">{description}</p>
                </div>
                <div className="flex flex-col items-end gap-2 text-xs font-mono">
                    <div className="flex items-center gap-2 text-slate-400 bg-slate-900/50 px-3 py-1.5 rounded-lg border border-slate-700/50">
                        <FileCode size={14} />
                        <span>{file}</span>
                    </div>
                    <div className="flex items-center gap-2 text-slate-500 px-2">
                        <span>{project}</span>
                    </div>
                </div>
            </div>

            <div className="bg-[#0d1117] rounded-lg p-6 overflow-x-auto border border-slate-800 shadow-inner">
                <CodeBlock code={code} />
            </div>
        </div>
    </motion.div>
);

export default Tutorial;
