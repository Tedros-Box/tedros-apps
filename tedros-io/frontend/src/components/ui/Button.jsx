import { motion } from 'framer-motion';

const Button = ({ children, variant = 'primary', className = '', ...props }) => {
    const baseStyles = "inline-flex items-center justify-center px-6 py-3 rounded-lg font-semibold cursor-pointer transition-all duration-300 border-none outline-none";

    const variants = {
        primary: "bg-gradient-to-r from-blue-500 to-violet-500 text-white shadow-lg shadow-blue-500/30 hover:-translate-y-0.5 hover:shadow-blue-500/50",
        outline: "bg-transparent border border-blue-500 text-blue-500 hover:bg-blue-500/10",
        ghost: "bg-transparent text-slate-300 hover:text-white hover:bg-white/5"
    };

    // Using inline styles/classes mapping since we don't have Tailwind yet, we map to CSS classes defined in index.css
    // Wait, I am restricted from adding Tailwind unless requested. I defined .btn classes in index.css.
    // Let's refactor this to use the CSS classes I defined in index.css instead of utility classes.

    let btnClass = 'btn';
    if (variant === 'primary') btnClass += ' btn-primary';
    else if (variant === 'outline') btnClass += ' btn-outline';

    return (
        <motion.button
            className={`${btnClass} ${className}`}
            whileHover={{ scale: 1.05 }}
            whileTap={{ scale: 0.95 }}
            {...props}
        >
            {children}
        </motion.button>
    );
};

export default Button;
