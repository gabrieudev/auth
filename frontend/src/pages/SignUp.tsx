import { Helmet } from "react-helmet-async";
import { z } from "zod";
import { zodResolver } from "@hookform/resolvers/zod";
import { useForm } from "react-hook-form";
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
import { signup } from "@/services/userService";
import { toast } from "sonner";
import { useNavigate, Link } from "react-router-dom";
import { Loader2 } from "lucide-react";
import { useMutation } from "@tanstack/react-query";

const signUpSchema = z.object({
  firstName: z
    .string()
    .min(3, { message: "O nome deve ter pelo menos 3 caracteres" }),
  lastName: z
    .string()
    .min(3, { message: "O sobrenome deve ter pelo menos 3 caracteres" }),
  email: z.string().email({ message: "Informe um e-mail válido" }),
  password: z
    .string()
    .min(6, { message: "A senha deve ter pelo menos 6 caracteres" }),
});

export default function SignUp() {
  const navigate = useNavigate();
  const signUpMutation = useMutation({
    mutationFn: signup,
    onSuccess: () => {
      toast.success("Cadastro realizado com sucesso!");
      navigate("/signin");
    },
    onError: () => {
      toast.error(signUpMutation.error?.message);
    },
  });

  const form = useForm<z.infer<typeof signUpSchema>>({
    resolver: zodResolver(signUpSchema),
    defaultValues: {
      firstName: "",
      lastName: "",
      email: "",
      password: "",
    },
  });

  async function onSubmit(values: z.infer<typeof signUpSchema>) {
    signUpMutation.mutate(values);
    form.reset();
    navigate("/signin");
  }

  return (
    <>
      <Helmet>
        <title>Cadastro</title>
        <meta name="description" content="Página de cadastro" />
        <meta name="keywords" content="cadastro, autenticação, auth" />
      </Helmet>

      <div className="h-screen flex flex-col items-center justify-center">
        <h1 className="text-3xl font-bold mb-4">Cadastro</h1>
        <div className="w-[300px] max-w-md">
          <Form {...form}>
            <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-8">
              <FormField
                control={form.control}
                name="firstName"
                render={({ field, fieldState }) => (
                  <FormItem>
                    <FormLabel>Nome</FormLabel>
                    <FormControl>
                      <Input type="text" placeholder="Nome" {...field} />
                    </FormControl>
                    {fieldState.error && (
                      <FormMessage>{fieldState.error.message}</FormMessage>
                    )}
                  </FormItem>
                )}
              />
              <FormField
                control={form.control}
                name="lastName"
                render={({ field, fieldState }) => (
                  <FormItem>
                    <FormLabel>Sobrenome</FormLabel>
                    <FormControl>
                      <Input type="text" placeholder="Sobrenome" {...field} />
                    </FormControl>
                    {fieldState.error && (
                      <FormMessage>{fieldState.error.message}</FormMessage>
                    )}
                  </FormItem>
                )}
              />
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
                <Button type="submit" disabled={signUpMutation.isPending}>
                  {signUpMutation.isPending ? (
                    <Loader2 className="animate-spin" />
                  ) : (
                    "Cadastrar"
                  )}
                </Button>
              </div>
              <p className="text-center mt-4">
                Já tem uma conta?{" "}
                <Link
                  className="text-primary-500 underline underline-offset-4 hover:no-underline"
                  to="/signin"
                >
                  Entre
                </Link>
              </p>
            </form>
          </Form>
        </div>
      </div>
    </>
  );
}
