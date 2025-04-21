import { Button } from "@/components/ui/button";
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "@/components/ui/form";
import { Input } from "@/components/ui/input";
import { useAuth } from "@/providers/AuthContext";
import { zodResolver } from "@hookform/resolvers/zod";
import { Loader2 } from "lucide-react";
import { Helmet } from "react-helmet-async";
import { useForm } from "react-hook-form";
import { Link, useNavigate } from "react-router-dom";
import { toast } from "sonner";
import { z } from "zod";
import { useMutation } from "@tanstack/react-query";

const signInSchema = z.object({
  email: z.string().email({ message: "Informe um e-mail válido" }),
  password: z
    .string()
    .min(6, { message: "A senha deve ter pelo menos 6 caracteres" }),
});

export default function SignIn() {
  const navigate = useNavigate();
  const { signIn } = useAuth();
  const signInMutation = useMutation({
    mutationFn: signIn,
    onSuccess: () => {
      toast.success("Login realizado com sucesso!");
      navigate("/dashboard");
    },
    onError: () => {
      toast.error(signInMutation.data || "Erro inesperado");
    },
  });

  const form = useForm<z.infer<typeof signInSchema>>({
    resolver: zodResolver(signInSchema),
    defaultValues: {
      email: "",
      password: "",
    },
  });

  async function onSubmit(values: z.infer<typeof signInSchema>) {
    signInMutation.mutate(values);
  }

  return (
    <>
      <Helmet>
        <title>Login</title>
        <meta name="description" content="Página de login" />
        <meta name="keywords" content="login, autenticação, auth" />
      </Helmet>

      <div className="h-screen flex flex-col items-center justify-center">
        <h1 className="text-3xl font-bold mb-4">Login</h1>
        <div className="w-[300px] max-w-md">
          <Form {...form}>
            <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-8">
              <FormField
                control={form.control}
                name="email"
                render={({ field, fieldState }) => (
                  <FormItem>
                    <FormLabel>E-mail</FormLabel>
                    <FormControl>
                      <Input type="email" placeholder="E-mail" {...field} />
                    </FormControl>
                    {fieldState.error && (
                      <FormMessage>{fieldState.error.message}</FormMessage>
                    )}
                  </FormItem>
                )}
              />
              <FormField
                control={form.control}
                name="password"
                render={({ field, fieldState }) => (
                  <FormItem>
                    <FormLabel>Senha</FormLabel>
                    <FormControl>
                      <Input type="password" placeholder="Senha" {...field} />
                    </FormControl>
                    {fieldState.error && (
                      <FormMessage>{fieldState.error.message}</FormMessage>
                    )}
                  </FormItem>
                )}
              />
              <div className="flex justify-center">
                <Button type="submit" disabled={signInMutation.isPending}>
                  {signInMutation.isPending ? (
                    <Loader2 className="animate-spin" />
                  ) : (
                    "Entrar"
                  )}
                </Button>
              </div>
              <p className="text-center mt-4">
                Não tem uma conta?{" "}
                <Link
                  className="text-primary-500 underline underline-offset-4 hover:no-underline"
                  to="/signup"
                >
                  Cadastre-se
                </Link>
              </p>
            </form>
          </Form>
        </div>
      </div>
    </>
  );
}
